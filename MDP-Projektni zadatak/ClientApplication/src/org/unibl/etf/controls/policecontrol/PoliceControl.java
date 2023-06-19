package org.unibl.etf.controls.policecontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.communication.Komunikacija;
import org.unibl.etf.controller.InitialFormController;
import org.unibl.etf.main.Main;
import org.unibl.etf.model.Person;
import org.unibl.etf.service.SendMessageService;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServicee;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeServiceLocator;
import org.unibl.etf.wantedlistregister.RegistarPotjernicaInterface;

public class PoliceControl {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String CLIENT_POLICY_FILE_PATH;
	private static String REGISTAR_POTJERNICA_SERVER_NAME;
	private static String REGISTAR_POTJERNICA_SERVER_PORT;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			CLIENT_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("CLIENT_POLICY_FILE_PATH");
			REGISTAR_POTJERNICA_SERVER_NAME = prop.getProperty("REGISTAR_POTJERNICA_SERVER_NAME");
			REGISTAR_POTJERNICA_SERVER_PORT = prop.getProperty("REGISTAR_POTJERNICA_SERVER_PORT");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}	
	private static final String OBUSTAVA_SAOBRACAJA_PORUKA = "OBUSTAVA SAOBRACAJA";


	public boolean daLiProlaziPolicijskuKontrolu(int jmb, String name, String surname) {
		System.setProperty("java.security.policy", CLIENT_POLICY_FILE_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry registry = LocateRegistry.getRegistry(2000);
			RegistarPotjernicaInterface registarPotjernica = (RegistarPotjernicaInterface) registry.lookup("RegistarPotjernica");
			if(registarPotjernica.isThePersonOnTheWantedList(jmb)) {
				if(name.isEmpty() && surname.isEmpty()) {
					//dohvati ime i prezime
					String nameAndSurname = registarPotjernica.getNameAndSurname(jmb);
					if(!("#").equals(nameAndSurname)) {
						name = nameAndSurname.split("#")[0];
						surname = nameAndSurname.split("#")[1];
					}
				}
				SavingInfoAboutPersonServiceeServiceLocator locator = new SavingInfoAboutPersonServiceeServiceLocator();
	            SavingInfoAboutPersonServicee service;
				service = locator.getSavingInfoAboutPersonServicee();
				Person person = new Person(true, jmb, name, surname);
				service.savePerson(person);
				
				Komunikacija.OBUSTAVA_SAOBRACAJA = true;
				
				String content = OBUSTAVA_SAOBRACAJA_PORUKA + " na terminalu " + InitialFormController.enteranceOrExitId + " je detektovana osoba sa potjernice.";
				//System.out.println("DETEKTOVAN OSOBA SA POTJRENICEEE ");
				new SendMessageService().sendMessage(content, SendMessageService.BROADCAST, InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
//				Thread.sleep(20000);
//				String contentNastavak = NASTAVAK_SAOBRACAJA_PORUKA;
//				System.out.println("NASTAVAK SAOBRACAJA");
//				Komunikacija.OBUSTAVA_SAOBRACAJA = false;
//				new SendMessageService().sendMessage(contentNastavak, SendMessageService.BROADCAST, InitialFormController.enteranceOrExitId);
				
			} else {
				return true;
			}
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
		return false;
	}
}
