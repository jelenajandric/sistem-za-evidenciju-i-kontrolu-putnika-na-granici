package org.unibl.etf.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.Person;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServicee;
import org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeServiceLocator;

public class RecordsOfChackedPersonsService {
	public List<Person> getRecordsOfChackedPersons() {
		List<Person> persons = null;
		SavingInfoAboutPersonServiceeServiceLocator locator = new SavingInfoAboutPersonServiceeServiceLocator();
		SavingInfoAboutPersonServicee service;
		try {
			service = locator.getSavingInfoAboutPersonServicee();
			persons = (List<Person>) Arrays.asList(service.getRecordsOfChackedPersons());
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return persons;
	}
}