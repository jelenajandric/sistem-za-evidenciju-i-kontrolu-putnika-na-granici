package org.unibl.etf.communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.soapservice.TerminalService;
import org.unibl.etf.soapservice.TerminalServiceServiceLocator;

public class StartinggTestAppService {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static String POKRENUTE_APLIKCIJE;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "ClientApplication" + File.separator + "resources" + File.separator + "config.properties"));
			POKRENUTE_APLIKCIJE = BASE_PATH + File.separator + "ClientApplication" + File.separator + prop.getProperty("POKRENUTE_APLIKCIJE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public int isTerminalExist(String terminalName) {
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			int terminalId = service.isTerminalExisting(terminalName);
			return terminalId;
		} catch (Exception e) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return -1;
		}
	} 
	
	public boolean isEnteranceOrExitExistAndActive(String entranceOrExitId, int terminalId) {
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			boolean daLiPostojiUlazIzlaz = service.isEnteranceOrExitExists(entranceOrExitId, terminalId);
			if(daLiPostojiUlazIzlaz ) {
				return areActiveBothPoliceAndCustomControl(entranceOrExitId);
			} else {
				return false;
			}
		} catch (Exception e) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
	
	private boolean areActiveBothPoliceAndCustomControl(String entranceOrExitId) {
		try {
			File f = new File(POKRENUTE_APLIKCIJE);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    String trimmedLine = currentLine.trim();
			    String[] niz = trimmedLine.split("#");
			    if(entranceOrExitId.equals(niz[0])) {
				    if(niz.length == 3) {
				    	reader.close();
				    	return true;
				    }
			    }
			}
			reader.close();
			
		}  catch (Exception e) {
			Logger.getLogger(Komunikacija.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	
	}

}
