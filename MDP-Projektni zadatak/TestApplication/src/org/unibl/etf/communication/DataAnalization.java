package org.unibl.etf.communication;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.controller.AddPersonalDataController;
import org.unibl.etf.controller.InitialFormController;
import org.unibl.etf.service.AddPersonalDataService;

import javafx.application.Platform;


public class DataAnalization {

	private AddPersonalDataService addPersonalDataService;
	
	public DataAnalization() {
		addPersonalDataService = new AddPersonalDataService();
	}
	
	private static final String POLICIJSKA="POLICIJSKA KONTROLA_";
	private static final String CARINSKA="CARINSKA KONTORLA_"; 
	private static final String DODAVANJE_FAJLOVA = "DODAVANJE FAJLOVA_";
	private static String OBUSTAVA_SAOBRACAJA_PORUKA = "Na snazi je obustava saobracaja";

	
	public void analyzeData(String data) {
		if(data!=null) {
			if(data.startsWith(POLICIJSKA)) {
				
				String daLiJeProsaoPolicijskuKOntrolu = data.split("_")[1];
				//System.out.println(POLICIJSKA + " " + daLiJeProsaoPolicijskuKOntrolu);
				if(Boolean.parseBoolean(daLiJeProsaoPolicijskuKOntrolu)) {
					addPersonalDataService.sendDataOnServerForCustomsControl(InitialFormController.enteranceOrExitId, AddPersonalDataController.jmb, AddPersonalDataController.name, AddPersonalDataController.surname, !Boolean.parseBoolean(daLiJeProsaoPolicijskuKOntrolu));				
					AddPersonalDataService.daLiJeProsaoPolicijskuKontrolu=true;
				} else {
					AddPersonalDataService.daLiJeProsaoPolicijskuKontrolu=false;
					Runnable task = () -> {
			            Platform.runLater(() -> {
			                AlertBox.display("Poruka", "Vi ste na potjernici . ");
			            });
			        };
			        Thread thread = new Thread(task);
			        thread.setDaemon(true);
			        thread.start();
				}
				
			} else if(data.startsWith(CARINSKA)) {
				
				String usefulContent = data.split("_")[1];
				//System.out.println(CARINSKA + " " + usefulContent);
				if(Boolean.parseBoolean(usefulContent)) {
				Runnable task = () -> {
		            Platform.runLater(() -> {
		                AlertBox.display("Poruka", "Uspjesno ste presli. ");
		            });
		        };
		        Thread thread = new Thread(task);
		        thread.setDaemon(true);
		        thread.start();
		        
				} else {
					
					Runnable task = () -> {
			            Platform.runLater(() -> {
			                AlertBox.display("Poruka", "NIste presli. ");
			            });
			        };
			        Thread thread = new Thread(task);
			        thread.setDaemon(true);
			        thread.start();
				}

			} else if(data.startsWith(DODAVANJE_FAJLOVA)) {
				
				String usefulContent = data.split("_")[1];
				//System.out.println(DODAVANJE_FAJLOVA + " " + usefulContent);
				if(Boolean.parseBoolean(usefulContent)) {
				Runnable task = () -> {
		            Platform.runLater(() -> {
		                AlertBox.display("Poruka", "Uspjesno ste dodali fajl. ");
		            });
		        };
		        Thread thread = new Thread(task);
		        thread.setDaemon(true);
		        thread.start();
				} else {
					Runnable task = () -> {
			            Platform.runLater(() -> {
			                AlertBox.display("Poruka", "Dodavanje fajla neuspjesno. ");
			            });
			        };
			        Thread thread = new Thread(task);
			        thread.setDaemon(true);
			        thread.start();
				}
				
			} else if(data.equals(OBUSTAVA_SAOBRACAJA_PORUKA)) {	
				Runnable task = () -> {
		            Platform.runLater(() -> {
		                AlertBox.display("Poruka", OBUSTAVA_SAOBRACAJA_PORUKA);
		            });
		        };
		        Thread thread = new Thread(task);
		        thread.setDaemon(true);
		        thread.start();
				
			}
		}
		
	}
}
