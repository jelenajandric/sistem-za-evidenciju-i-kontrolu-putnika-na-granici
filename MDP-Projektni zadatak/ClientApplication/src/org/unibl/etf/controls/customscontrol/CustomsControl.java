package org.unibl.etf.controls.customscontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.fileserver.FileServerInterface;
import org.unibl.etf.main.Main;
import org.unibl.etf.model.Person;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServicee;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeServiceLocator;

public class CustomsControl {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String CLIENT_POLICY_FILE_PATH;
	//private static String FILE_SERVER_NAME;
	//private static String FILE_SERVER_PORT;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			CLIENT_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("CLIENT_POLICY_FILE_PATH");
			//FILE_SERVER_NAME = prop.getProperty("FILE_SERVER_NAME");
			//FILE_SERVER_PORT = prop.getProperty("FILE_SERVER_PORT");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public boolean addDataAboutPerson(int jmb, String name, String surname, boolean isItOnTheWantedList) {
		try {
			SavingInfoAboutPersonServiceeServiceLocator locator = new SavingInfoAboutPersonServiceeServiceLocator();
            SavingInfoAboutPersonServicee service;
            //System.out.println("Carinska kontrolaaaa");
			service = locator.getSavingInfoAboutPersonServicee();
			Person person = new Person(isItOnTheWantedList, jmb, name, surname);
			if(!isItOnTheWantedList) {
				service.savePerson(person);
			}
			return isItOnTheWantedList;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
		
	}
	
	public boolean saveFile(Integer[] readedBytes, int jmb, String fileName) {
		System.setProperty("java.security.policy", CLIENT_POLICY_FILE_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			FileServerInterface fajlServer = (FileServerInterface) registry.lookup("FajlServer");
			//System.out.println("Poziv fajl servera sa carniske kontrole ");
			fajlServer.saveFile(readedBytes, jmb,fileName);
			return true;
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return false;
		}
	}
}
