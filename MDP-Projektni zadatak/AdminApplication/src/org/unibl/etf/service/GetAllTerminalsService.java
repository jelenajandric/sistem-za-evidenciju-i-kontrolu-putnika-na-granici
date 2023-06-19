package org.unibl.etf.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.Terminal;
import org.unibl.etf.soapservice.TerminalService;
import org.unibl.etf.soapservice.TerminalServiceServiceLocator;

public class GetAllTerminalsService {
	public List<Terminal> getAllTerminals() {
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		List<Terminal> terminals = null;
		try {
			service = locator.getTerminalService();
			Terminal[] terminalArray = service.allTerminals();
			if(terminalArray!=null) {
				terminals = (List<Terminal>) Arrays.asList(terminalArray);
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return terminals;
	}
}
