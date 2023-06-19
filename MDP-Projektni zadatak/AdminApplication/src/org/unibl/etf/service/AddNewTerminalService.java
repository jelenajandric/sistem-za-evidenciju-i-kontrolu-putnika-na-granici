package org.unibl.etf.service;

import org.unibl.etf.soapservice.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.Terminal;

public class AddNewTerminalService {
	public void saveNewTerminal(int numOfEnterances, int numOfExits, String name) {
		Terminal terminal = new Terminal(0, name, numOfEnterances, numOfExits);
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			service.createTerminal(terminal, true);
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
