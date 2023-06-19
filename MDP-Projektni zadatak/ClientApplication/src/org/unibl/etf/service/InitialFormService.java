package org.unibl.etf.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.InitialFormResponse;
import org.unibl.etf.soapservice.TerminalService;
import org.unibl.etf.soapservice.TerminalServiceServiceLocator;


public class InitialFormService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String POKRENUTE_APLIKACIJE;
	private Properties prop;
	
	public InitialFormService() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			POKRENUTE_APLIKACIJE = BASE_PATH + prop.getProperty("POKRENUTE_APLIKCIJE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public InitialFormResponse dataVerification(String terminalName, String enteranceOrExitId, String enteranceOrExitControlType) { 
		InitialFormResponse response=new InitialFormResponse();
	
		TerminalServiceServiceLocator locator = new TerminalServiceServiceLocator();
		TerminalService service;
		try {
			service = locator.getTerminalService();
			int idTerminala = service.isTerminalExisting(terminalName);
			if(-1!=idTerminala) {
				boolean daLiPostojiUlazIzlaz = service.isEnteranceOrExitExists(enteranceOrExitId, idTerminala);
				if(daLiPostojiUlazIzlaz) {
					try {
						File f = new File(POKRENUTE_APLIKACIJE);
						boolean imaTajUlazIzlaz = false;
						BufferedReader reader = new BufferedReader(new FileReader(f));
						String currentLine;
						while((currentLine = reader.readLine()) != null) {
						    String trimmedLine = currentLine.trim();
						    String[] niz = trimmedLine.split("#");
						    if(enteranceOrExitId.equals(niz[0])) {
						    	imaTajUlazIzlaz=true;
							    if(niz.length == 3) {
							    	//inace niz se sastoji od id#p#c pa ako je 3 pokrenut je i policijski i carniski
							    	response.setMessage("Za ovaj ulaz/izlaz je vec pokrenuta i policijska i carinska aplikacija.");
							    	response.setIndicator(false);
							    } else if(niz.length==2 && enteranceOrExitControlType.equals(niz[1])) {
							    	//ako je bas taj tip kontrole koji hocemo da pokrenemo pokrenut vec
							    	response.setMessage("Ovaj tip kontrole je vec pokrenut.");
							    	response.setIndicator(false);
							    } else {
							    	//sad na tu liniju treba upisat "#" + tipUlazaIzlaza ucitacu sve u arraylist 
							    	//activateNewControlApp(enteranceOrExitId, enteranceOrExitControlType);	
							    	imaTajUlazIzlaz=false;
							    	break;
							    }
						    } 
						}
						reader.close();
						if(!imaTajUlazIzlaz) {
							//activateNewControlApp(enteranceOrExitId, enteranceOrExitControlType);
							response.setIndicator(true);
					    	response.setMessage("Uspjesno ste pristupili aplikaciji.");
						}
					} catch (IOException e) {
						Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
					
				} else {
					response.setMessage("Ne postoji ulaz/izlaz sa unesenim id.");
			    	response.setIndicator(false);
				}
			} else {
				response.setMessage("Ne postoji terminal sa tim nazivom.");
		    	response.setIndicator(false);
			}
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		return response;
	}
	
	public void addNewClientControlApp(String enteranceOrExitId,String enteranceOrExitControlType) {
		try {
			boolean indikator = false;
			File f = new File(POKRENUTE_APLIKACIJE);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			ArrayList<String> listaLinija = new ArrayList<String>();
			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
			    if(enteranceOrExitId.equals(trimmedLine.split("#")[0])) {
			    	trimmedLine = trimmedLine.concat("#" + enteranceOrExitControlType);
			    	indikator = true;
			    }
			    listaLinija.add(trimmedLine);
			}
			if(!indikator) {
				listaLinija.add(enteranceOrExitId+"#"+enteranceOrExitControlType);
				indikator=true;
			}
			reader.close();
			PrintWriter out = new PrintWriter(new FileWriter(f, false));
			for(String s:listaLinija) {
				out.println(s);
			}
			out.close();
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public void closeThisClientControlApp(String enteranceOrExitId,String enteranceOrExitControlType) {
		try {
			File f = new File(POKRENUTE_APLIKACIJE);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			ArrayList<String> sveLinije = new ArrayList<String>();
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
			    String trimmedLine = currentLine.trim();
			    String[] niz = trimmedLine.split("#");
			    if(enteranceOrExitId.equals(niz[0])) {
				    if(niz.length == 3) {
				    	if(enteranceOrExitControlType.equals(niz[2])) {
				    		trimmedLine= trimmedLine.substring(0,trimmedLine.length()-2);
					    	//oduzmem zadnja dva karaktera a to su #c ili #p
					    	sveLinije.add(trimmedLine);
				    	} else {
				    		String novaLinija = niz[0] + "#" +niz[2];
				    		sveLinije.add(novaLinija);
				    	}
				    }
			    } else {
			    	sveLinije.add(trimmedLine);
			    }
			}
			reader.close();
			PrintWriter out = new PrintWriter(new FileWriter(f, false));
			for(String s:sveLinije) {
				out.println(s);
			}
			out.close();
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
