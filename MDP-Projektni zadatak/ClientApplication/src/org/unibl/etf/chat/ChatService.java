package org.unibl.etf.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatService {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static String PATH_TO_CHAT_DIR;
	
	public static Handler handler;
	static {
		try {
			handler = new FileHandler("logs/clientchat.log");
			Logger.getLogger(ChatService.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			PATH_TO_CHAT_DIR = BASE_PATH + prop.getProperty("PATH_TO_CHAT_DIR");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public void saveMessageIntoFile(String receiver, String content, String sender ) {
		try {
			File f = new File(PATH_TO_CHAT_DIR + File.separator + receiver);
			if(!f.exists()) {
				f.createNewFile();
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			PrintWriter out = new PrintWriter(new FileWriter(f, true));
			out.println(content + "_" + sender + "_" + dtf.format(now));
			out.close();
		} catch (Exception e) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
