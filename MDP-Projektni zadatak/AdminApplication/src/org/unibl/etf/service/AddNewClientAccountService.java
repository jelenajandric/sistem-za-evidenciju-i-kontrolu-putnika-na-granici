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


public class AddNewClientAccountService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String BASE_URL;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			BASE_URL = prop.getProperty("BASE_URL_IMDB");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public void createClient(String clientJsonString) {
		try {
			URL url = new URL(BASE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			JSONObject input = new JSONObject(clientJsonString);
			
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
}
