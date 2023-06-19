package org.unibl.etf.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.unibl.etf.main.Main;

public class LoginService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String IN_MEMORY_DB_URL;
	private static String USERNAME_APP_MATCHING;
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			IN_MEMORY_DB_URL = prop.getProperty("IN_MEMORY_DB_URL");
			USERNAME_APP_MATCHING = BASE_PATH + File.separator + prop.getProperty("USERNAME_APP_MATCHING");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public boolean validateInfo(String username, String password) {
		try {
			URL url = new URL(IN_MEMORY_DB_URL + username + "/validate");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT"); 
			conn.setRequestProperty("Content-Type", "application/json");
			JSONObject input = new JSONObject("{\"password\":\"" + password +"\"}");
			OutputStream os = conn.getOutputStream();
			os.write(input.toString().getBytes());
			os.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
	
	public boolean isClientAlreadyLogin(String username) {
		try {
			File f = new File(USERNAME_APP_MATCHING);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				String usernameFromFile = trimmedLine.split(":")[0];
				
			    if(usernameFromFile.equals(username)) {
			    	reader.close();
			    	return true;
			    }
			}
			reader.close();
		} catch (NumberFormatException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}
	
	public void saveClientToUsernameAppMatching(String username, String entranceOrExitIdAndType) {
		try {
			File f = new File(USERNAME_APP_MATCHING);
			PrintWriter out = new PrintWriter(new FileWriter(f, true));
			out.println(username + ":" + entranceOrExitIdAndType);
			out.close();
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public void removeClientFromUsernameAppMatching(String username, String entranceOrExitIdAndType) {
		try {
			File f = new File(USERNAME_APP_MATCHING);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			ArrayList<String> sveLinije = new ArrayList<String>();
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    String trimmedLine = currentLine.trim();
			    String[] niz = trimmedLine.split(":");
			    if(!username.equals(niz[0])) {
			    	sveLinije.add(trimmedLine);
			    }
			}
			reader.close();
			PrintWriter out = new PrintWriter(new FileWriter(f, false));
			for(String s:sveLinije) {
				out.println(s);
			}
			out.close();
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
} 
