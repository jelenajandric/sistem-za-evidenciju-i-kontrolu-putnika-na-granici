package org.unibl.etf.communication;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.controller.AddPersonalDataController;
import org.unibl.etf.main.Main;
import org.unibl.etf.sender.DataSenderr;
import org.unibl.etf.sender.DataSenderrServiceLocator;

public class KomunikacijaTestApplication extends Thread {
	
	
	@Override
	public void run() {
		DataSenderrServiceLocator locator = new DataSenderrServiceLocator();
		DataSenderr service;
		while(true) {
			try {
				service = locator.getDataSenderr();
				
				String responseString = service.imaLiStaZaTestnuAplikaciju(AddPersonalDataController.jmb);
				if(responseString!=null && !responseString.isEmpty())
					new DataAnalization().analyzeData(responseString);
				//Thread.sleep(4000);
			} catch (Exception e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
			}
		}
	}
}
