package org.unibl.etf.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.ClientMessage;

public class GetAllMessagesService {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static String PATH_TO_CHAT_DIR;
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			PATH_TO_CHAT_DIR = BASE_PATH + prop.getProperty("PATH_TO_CHAT_DIR");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public ArrayList<ClientMessage> getAllMessages(String entranceOrExitIdAndType) {
		ArrayList<ClientMessage> messages = new ArrayList<ClientMessage>();
		File f = new File(PATH_TO_CHAT_DIR + File.separator + entranceOrExitIdAndType);
		if(!f.exists()) {
			return null;
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = br.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				String[] niz=trimmedLine.split("_");
				messages.add(new ClientMessage(niz[1], niz[0], niz[2]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return messages;
	}

}
