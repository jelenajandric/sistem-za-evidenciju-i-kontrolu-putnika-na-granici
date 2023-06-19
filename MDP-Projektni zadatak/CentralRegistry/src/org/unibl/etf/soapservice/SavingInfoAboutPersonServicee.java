package org.unibl.etf.soapservice;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.model.Person;

public class SavingInfoAboutPersonServicee {
	private static final String BASE_PATH = System.getProperty("user.dir");
	
	private static String OSOBE_SA_POTJERNICE_DIR;
	private static String PROCESIRANE_OSOBE;
	
	public static Handler handler;
	static {
		try {
			java.nio.file.Path p = Paths.get("logs/centralregistry_savinginfo.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + File.separator + "CentralRegistry" + File.separator + "resources" + File.separator + "config.properties"));
			PROCESIRANE_OSOBE = BASE_PATH + prop.getProperty("PROCESIRANE_OSOBE");
			OSOBE_SA_POTJERNICE_DIR = BASE_PATH + prop.getProperty("OSOBE_SA_POTJERNICE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}


	public boolean savePerson(Person person) {
		File f = new File(PROCESIRANE_OSOBE);
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(f, true));
			out.println(person);
			out.close();
			if(person.isItOnTheWantedList()) {
				savePersonOnWantedList(person);
			}
			return true;
		} catch (IOException e) {
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
	
	private boolean savePersonOnWantedList(Person person) {
		try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(new File(OSOBE_SA_POTJERNICE_DIR + person.getJmbg()+".txt"), true));
			encoder.writeObject(person);
			encoder.close();
			return true;
		} catch (Exception e) {
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}
	
	public Person[] getRecordsOfChackedPersons() {
		ArrayList<Person> persons = new ArrayList<Person>();
		Person[] personsArray = null;
		try {
			File f = new File(PROCESIRANE_OSOBE);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null ) {
			    String trimmedLine = currentLine.trim();
			    String[] info = trimmedLine.split("#");
			    Person p = new Person(Integer.parseInt(info[0]), info[1], info[2], Boolean.parseBoolean(info[3]));
			    persons.add(p);
			}
			reader.close();
			personsArray = new Person[persons.size()];
			int i=0;
			for(Person person : persons) {
				personsArray[i] = person;
				i++;
			}
		} catch (IOException e) {
			Logger.getLogger(SavingInfoAboutPersonServicee.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return personsArray;
	}
}
