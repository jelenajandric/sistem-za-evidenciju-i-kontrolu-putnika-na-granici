package org.unibl.etf.chat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClientChatSender {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static String HOST;
	private static int PORT;
	private static String KEY_STORE_PATH;
	private static String KEY_STORE_PASSWORD;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			HOST = prop.getProperty("HOST");
			PORT = Integer.parseInt(prop.getProperty("SENDER_PORT"));
			KEY_STORE_PATH = BASE_PATH + prop.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = prop.getProperty("KEY_STORE_PASSWORD");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public static void sendMessageOnChatServer(String content) throws IOException { 
		
		System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
		
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket s = (SSLSocket) sf.createSocket(HOST, PORT);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
		//System.out.println("salje se poruka  " + content);
		out.println(content);
		
	}
}
