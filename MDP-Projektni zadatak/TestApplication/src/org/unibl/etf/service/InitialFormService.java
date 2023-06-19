package org.unibl.etf.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.InitialFormResponse;
import org.unibl.etf.sender.DataSenderr;
import org.unibl.etf.sender.DataSenderrServiceLocator;

public class InitialFormService {
	public InitialFormResponse verifieData(String terminalName, String enteranceOrExitId) {
		InitialFormResponse response = new InitialFormResponse();
		DataSenderrServiceLocator locator = new DataSenderrServiceLocator();
		DataSenderr service;
		try {
			service = locator.getDataSenderr();
			int terminalId = service.daLiPostojiTerminal(terminalName);
			if(terminalId!=-1) {
				boolean daLiPostojiUlazIzlaz = service.daLiPostojiAktivanUlazIzlaz(enteranceOrExitId, terminalId);
				if(daLiPostojiUlazIzlaz) {
					response.setIndicator(true);
					response.setMessage("Uspjesno ste usli na terminal sa unijetim podacima.");
					return response;
				} else {
					response.setIndicator(false);
					response.setMessage("Id ulaza/izlaza koji ste unijeli nije aktivan ili ne postoji.");
					return response;
				}
			} else {
				response.setIndicator(false);
				response.setMessage("Terminal sa tim nazivom ne postoji.");
				return response;
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return response;
	}
}
