package org.unibl.etf.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.unibl.etf.main.Main;

public class ChangePasswordService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String IN_MEMORY_DB_URL;
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			IN_MEMORY_DB_URL = prop.getProperty("IN_MEMORY_DB_URL");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}

	public void updateClient(String korisnickoIme, String lozinka) {
		try {
			URL url = new URL(IN_MEMORY_DB_URL + korisnickoIme);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT"); 
			conn.setRequestProperty("Content-Type", "application/json");
			JSONObject input = new JSONObject("{\"password\":\"" + lozinka +"\"}");
			
			OutputStream os = conn.getOutputStream();
			os.write(input.toString().getBytes());
			os.flush();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					//System.out.println(output);
				}
				os.close();
				br.close();
				conn.disconnect();
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public boolean validateInfo(String korisnickoIme, String lozinka) {
		try {
			
			URL url = new URL(IN_MEMORY_DB_URL + korisnickoIme + "/validate");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT"); 
			conn.setRequestProperty("Content-Type", "application/json");
			JSONObject input = new JSONObject("{\"password\":\"" + lozinka +"\"}");

			OutputStream os = conn.getOutputStream();
			os.write(input.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
}
