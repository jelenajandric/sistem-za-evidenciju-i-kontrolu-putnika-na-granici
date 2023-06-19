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

import org.unibl.etf.chat.ClientChatSender;
import org.unibl.etf.main.Main;

public class SendMessageService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String POKRENUTE_APLIKACIJE;
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			POKRENUTE_APLIKACIJE = BASE_PATH + prop.getProperty("POKRENUTE_APLIKCIJE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	
	public static final String BROADCAST = "SVI";
	public static final String MULTICAST = "SVI SA OVOG TERMINALA";
	
	public ArrayList<String> getActiveEnerancesAndExitsList(String entranceOrExitIdAndType) {
		ArrayList<String> activeEnerancesAndExits = new ArrayList<String>();
		try {
			File f = new File(POKRENUTE_APLIKACIJE);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
		
			while((currentLine = reader.readLine()) != null) {
			    String trimmedLine = currentLine.trim();
			    String[] niz = trimmedLine.split("#");
			    if(niz.length==3) {
			    	String prviUlazIzlaz = niz[0] + niz[1];
			    	String drugiUlazIzlaz = niz[0] + niz[2];
			    	if(!prviUlazIzlaz.equals(entranceOrExitIdAndType)) {
			    		activeEnerancesAndExits.add(prviUlazIzlaz);
			    	}
			    	if(!drugiUlazIzlaz.equals(entranceOrExitIdAndType)) {
			    		activeEnerancesAndExits.add(drugiUlazIzlaz);
			    	}
			    } else if(niz.length==2) {
			    	String prviUlazIzlaz = niz[0] + niz[1];
			    	if(!prviUlazIzlaz.equals(entranceOrExitIdAndType)) {
			    		activeEnerancesAndExits.add(prviUlazIzlaz);
			    	}
			    }
			}
			reader.close();
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return activeEnerancesAndExits;
	}
	
	public void sendMessage(String messageContent, String receiver, String enteranceOrExitIdAndType) {
		if(receiver.equals(BROADCAST)) {
			ArrayList<String> activeEnerancesAndExits = getActiveEnerancesAndExitsList(enteranceOrExitIdAndType);
			for(int i=0; i<activeEnerancesAndExits.size();i++) {
				try {
					ClientChatSender.sendMessageOnChatServer(activeEnerancesAndExits.get(i) + "_" + messageContent+ "_" + enteranceOrExitIdAndType);
				} catch (IOException e) {
					Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
			}
			
		} else if(receiver.equals(MULTICAST)) {
			String idTerminalaZaOvajUlazIzlaz=getTerminalId(enteranceOrExitIdAndType);
			ArrayList<String> activeEnerancesAndExits= getActiveEnerancesAndExitsList(enteranceOrExitIdAndType);
			for(int i=0; i<activeEnerancesAndExits.size();i++) {
				String idTerminala=getTerminalId(activeEnerancesAndExits.get(i));
				if(idTerminala.equals(idTerminalaZaOvajUlazIzlaz)) {
					try {
						ClientChatSender.sendMessageOnChatServer(activeEnerancesAndExits.get(i) + "_" + messageContent + "_" + enteranceOrExitIdAndType);
					} catch (IOException e) {
						Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
				}
			}
		} else {
			try {
				ClientChatSender.sendMessageOnChatServer(receiver+"_" +messageContent + "_" + enteranceOrExitIdAndType);
			} catch (IOException e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
	}
	
	public String getTerminalId(String enteranceOrExitId) {
		String idTerminala="";
		if(enteranceOrExitId.contains("U")) {
			idTerminala = enteranceOrExitId.split("U")[0];
		} else {
			idTerminala = enteranceOrExitId.split("I")[0];
		}
		return idTerminala;
	}
}
