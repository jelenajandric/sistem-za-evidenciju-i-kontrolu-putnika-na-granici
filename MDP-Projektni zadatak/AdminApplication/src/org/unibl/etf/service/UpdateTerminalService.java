package org.unibl.etf.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.Terminal;
import org.unibl.etf.soapservice.TerminalService;
import org.unibl.etf.soapservice.TerminalServiceServiceLocator;


public class UpdateTerminalService {
	public void saveChanges(int id, int numOfEnterances, int numOfExits, String name) {
		Terminal terminal = new Terminal(id, name, numOfEnterances, numOfExits);
		
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			service.updateTerminal(terminal);
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
