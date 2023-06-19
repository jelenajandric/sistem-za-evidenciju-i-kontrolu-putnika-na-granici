package org.unibl.etf.communication;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.controller.InitialFormController;
import org.unibl.etf.model.DataAnalyzationResponse;
import org.unibl.etf.receiver.SomeStupidReceiver;
import org.unibl.etf.receiver.SomeStupidReceiverServiceLocator;
import org.unibl.etf.service.SendMessageService;

public class Komunikacija extends Thread {
	
	public static Handler handler;
	{
		try {
			handler = new FileHandler("logs/clientcommunication.log");
			Logger.getLogger(Komunikacija.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String POLICIJSKA="POLICIJSKA KONTROLA_";
	private static final String CARINSKA="CARINSKA KONTORLA_"; 
	private static final String DODAVANJE_FAJLOVA = "DODAVANJE FAJLOVA_";
	
	public static boolean OBUSTAVA_SAOBRACAJA = false;
	private static String OBUSTAVA_SAOBRACAJA_PORUKA = "Na snazi je obustava saobracaja";
	private static final String NASTAVAK_SAOBRACAJA_PORUKA = "NASTAVAK SAOBRACAJA";

	@Override
	public void run() {
		while(true) {
			SomeStupidReceiverServiceLocator locator = new SomeStupidReceiverServiceLocator();
			SomeStupidReceiver service;
			try {
				service = locator.getSomeStupidReceiver();
				String s = "";
				if(InitialFormController.enteranceOrExitControlType.equals("p")) {
					s = service.imaLiStaZaPolicijskuKontrolu(InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
				} else {
					s = service.imaLiStaZaCarinskuKontrolu(InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
				}
				
				if(!OBUSTAVA_SAOBRACAJA) {
					if(!s.isEmpty() && s!=null)  {
						DataAnalyzationResponse response = new DataAnalizationUtil().analyzeData(s);
						if(response.getControlType().equals(POLICIJSKA)) {
							
							service.dodajOdgovorOdPolicijskeKontrole(response.getJmb(), InitialFormController.enteranceOrExitId, response.isIndicator());
							if(!response.isIndicator()) { //ako nije prosao pol kontrolu
								Thread.sleep(20000);
								String contentNastavak = NASTAVAK_SAOBRACAJA_PORUKA;
								OBUSTAVA_SAOBRACAJA = false;
								new SendMessageService().sendMessage(contentNastavak, SendMessageService.BROADCAST, InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
							}
							
						} else if(response.getControlType().equals(CARINSKA)) {
							service.dodajOdgovorOdCarinskeKontrole(response.getJmb(), InitialFormController.enteranceOrExitId, response.isIndicator());
						} else if(response.getControlType().contentEquals(DODAVANJE_FAJLOVA))  {
							service.dodajOdgovorOdDodavanjaFajlaa(response.getJmb(), InitialFormController.enteranceOrExitId, response.isIndicator());
						}
					} 
				} else if(!s.isEmpty() && s!=null && OBUSTAVA_SAOBRACAJA) {
					service.dodajOdgovorKadJeObustavaSaobracaja(Integer.parseInt(s.split("_")[1]), InitialFormController.enteranceOrExitId, OBUSTAVA_SAOBRACAJA_PORUKA);
				}
				
				Thread.sleep(3000);
			} catch (Exception e) {
				Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
	}
}
