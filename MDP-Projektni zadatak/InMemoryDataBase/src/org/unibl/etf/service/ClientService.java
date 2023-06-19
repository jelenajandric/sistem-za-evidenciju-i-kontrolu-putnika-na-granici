package org.unibl.etf.service;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.model.Client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ClientService {
	
	public static String instanceName = "Klijent";
	public static final String HOST = "localhost";
	
	public boolean addClient(Client client) {
		JedisPool pool = new JedisPool(HOST);
		long len=0, newlen =0;
		try (Jedis jedis = pool.getResource()) {
			len = jedis.llen(instanceName + ":clients:strings");
			newlen= jedis.lpush(instanceName + ":clients:strings", client.toString());
		}
		pool.close();
		return newlen>len;
	}
	
	public ArrayList<Client> getAll() {
		List<String> clients;
		JedisPool pool = new JedisPool(HOST);
		try (Jedis jedis = pool.getResource()) {
			long len = jedis.llen(instanceName + ":clients:strings");
			clients = jedis.lrange(instanceName + ":clients:strings", 0 ,len); 
		}
		pool.close();
		ArrayList<Client> klijenti = new ArrayList<Client>();
		for(String k:clients) {
			klijenti.add(new Client(k.split(" ")[0], k.split(" ")[1]));
		}
		return klijenti;	
	}
	
	public Client validateInfo(String username, String password) {
		ArrayList<Client> klijenti = getAll();
		for(Client c:klijenti) {
			if(c.getUsername().equals(username) && c.getPassword().equals(password) ) {
				return new Client(username, password);
			}
		}
		return null;
	}
	
	public boolean deleteClient(String username) {
		List<String> klijenti = new ArrayList<String>();
		String k = "";
		JedisPool pool = new JedisPool(HOST);
		try (Jedis jedis = pool.getResource()) {
			while((k=jedis.rpop(instanceName + ":clients:strings"))!=null) {
				klijenti.add(k);
			}
		}
		pool.close();

		for(String k1:klijenti) {
			if(!(username.equals(k1.split(" ")[0]))) {
				 addClient(new Client(k1.split(" ")[0], k1.split(" ")[1]));
			}
		}
		return true;
	}
	
	public boolean updatePassword(String username, String newPass) {
		List<String> klijenti = new ArrayList<String>();
		String k = "";
		JedisPool pool = new JedisPool(HOST);
		try (Jedis jedis = pool.getResource()) {
			while((k=jedis.rpop(instanceName + ":clients:strings"))!=null) {
				klijenti.add(k);
			}
		}
		pool.close();

		for(String k1:klijenti) {
			if(!(username.equals(k1.split(" ")[0]))) {
				 addClient(new Client(k1.split(" ")[0], k1.split(" ")[1]));
			}
		}
		addClient(new Client(username, newPass));
		return true;
	}
	
	
	

}
