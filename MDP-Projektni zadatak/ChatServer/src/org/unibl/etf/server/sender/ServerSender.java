package org.unibl.etf.server.sender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.unibl.etf.server.main.MainChatServer;

public class ServerSender {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static int PORT;
	private static String KEY_STORE_PATH;
	private static String KEY_STORE_PASSWORD;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			PORT = Integer.parseInt(prop.getProperty("SENDER_PORT"));
			KEY_STORE_PATH = BASE_PATH + prop.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = prop.getProperty("KEY_STORE_PASSWORD");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}

	public ServerSender() {
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
		startServer();
	}
	
	private void startServer()  {
		try {
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ServerSocket ss = ssf.createServerSocket(PORT);
			System.out.println("Server je pokrenut");
			while (true) {
				SSLSocket s = (SSLSocket) ss.accept();
				new ServerThreadSender(s).start();
			}
		} catch (Exception e) {
			Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	
}
