package org.unibl.etf.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.communication.KomunikacijaTestApplication;
import org.unibl.etf.main.Main;
import org.unibl.etf.sender.DataSenderr;
import org.unibl.etf.sender.DataSenderrServiceLocator;

public class AddPersonalDataService {
	public static boolean daLiJeProsaoPolicijskuKontrolu = false;
	private int jmbStari;
	
	public void sendDataOnServerForPoliceControl(String enteranceOrExitId, int jmb, String name, String surname) {
		daLiJeProsaoPolicijskuKontrolu=false;
		jmbStari=jmb;
		DataSenderrServiceLocator locator = new DataSenderrServiceLocator();
		DataSenderr service;
		try {
			service = locator.getDataSenderr();
			service.dodajPodatkeZaSlanjeNaPolicijskuKontrolu(enteranceOrExitId+ "p", jmb, name, surname);
			new KomunikacijaTestApplication().start();
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public void sendDataOnServerForCustomsControl(String enteranceOrExitId, int jmb, String name, String surname, boolean isItOnTheWantedList) {
		DataSenderrServiceLocator locator = new DataSenderrServiceLocator();
		DataSenderr service;
		try {
			service = locator.getDataSenderr();
			service.dodajPodatkeZaSlanjeNaCarinskuKontrolu(enteranceOrExitId+"c", jmb, name, surname, isItOnTheWantedList);
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
	}
	
	public void saveFile(int[] vec1, int jmb, String fileName, String entranceOrExitId) {
		if(daLiJeProsaoPolicijskuKontrolu && jmbStari==jmb) {
			DataSenderrServiceLocator locator = new DataSenderrServiceLocator();
			DataSenderr service;
			try {
				service = locator.getDataSenderr();
				service.dodajPodatkeZaDodavanjeFajlovaa(entranceOrExitId+"c", jmb, vec1, fileName);
			} catch (Exception e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		} else {
			AlertBox.display("Obavjestenje", "Morate proci policijsku kontrolu da biste dodali fajl ");
		}
	}

}
