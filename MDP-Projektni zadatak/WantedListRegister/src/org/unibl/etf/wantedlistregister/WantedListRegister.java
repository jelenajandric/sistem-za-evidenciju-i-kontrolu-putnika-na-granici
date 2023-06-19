package org.unibl.etf.wantedlistregister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WantedListRegister implements RegistarPotjernicaInterface {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String POTJERNICE_PATH;
	private static String SERVER_POLICY_FILE_PATH;
	private static String WANTED_LIST_REGISTER_SERVER_NAME;
	private static String WANTED_LIST_REGISTER_SERVER_PORT;

	public static Handler handler;
	static {
		try {
			java.nio.file.Path p = Paths.get("logs/wantedlistregister.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(WantedListRegister.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			POTJERNICE_PATH = BASE_PATH + prop.getProperty("POTJERNICE_PATH");
			SERVER_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("SERVER_POLICY_FILE_PATH");
			WANTED_LIST_REGISTER_SERVER_NAME = prop.getProperty("WANTED_LIST_REGISTER_SERVER_NAME");
			WANTED_LIST_REGISTER_SERVER_PORT = prop.getProperty("WANTED_LIST_REGISTER_SERVER_PORT");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public boolean isThePersonOnTheWantedList(int jmb) {
		try {
			File f = new File(POTJERNICE_PATH);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				int id = Integer.parseInt(trimmedLine.split("#")[0]);
			    if(id==jmb) {
			    	reader.close();
			    	return true;
			    }
			}
			reader.close();
		} catch (NumberFormatException e) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}
	
	public String getNameAndSurname(int jmb) {
		try {
			File f = new File(POTJERNICE_PATH);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				int id = Integer.parseInt(trimmedLine.split("#")[0]);
			    if(id==jmb) {
			    	reader.close();
			    	return trimmedLine.split("#")[1] + "#" + trimmedLine.split("#")[2];
			    }
			}
			reader.close();
		} catch (NumberFormatException e) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return "#";
	}

	public static void main(String args[]) {
//		Properties prop = new Properties();
//		try {
//			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
//			POTJERNICE_PATH = BASE_PATH + prop.getProperty("POTJERNICE_PATH");
//			SERVER_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("SERVER_POLICY_FILE_PATH");
//			WANTED_LIST_REGISTER_SERVER_NAME = prop.getProperty("WANTED_LIST_REGISTER_SERVER_NAME");
//			WANTED_LIST_REGISTER_SERVER_PORT = prop.getProperty("WANTED_LIST_REGISTER_SERVER_PORT");
//		} catch (FileNotFoundException e1) {
//			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
//		} catch (IOException e1) {
//			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
//		}
		
		System.setProperty("java.security.policy", SERVER_POLICY_FILE_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			WantedListRegister server = new WantedListRegister();
			RegistarPotjernicaInterface stub = (RegistarPotjernicaInterface) UnicastRemoteObject.exportObject(server,0);
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(WANTED_LIST_REGISTER_SERVER_PORT));
			registry.rebind(WANTED_LIST_REGISTER_SERVER_NAME, stub);
			System.out.println("Server RegistarPotjernica started.");
		} catch (Exception ex) {
			Logger.getLogger(WantedListRegister.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}
}
