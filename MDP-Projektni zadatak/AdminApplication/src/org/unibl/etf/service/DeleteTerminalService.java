package org.unibl.etf.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.soapservice.TerminalService;
import org.unibl.etf.soapservice.TerminalServiceServiceLocator;

public class DeleteTerminalService {

	public void deleteTerminal(int id) {
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			service.deleteTerminal(id);
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
