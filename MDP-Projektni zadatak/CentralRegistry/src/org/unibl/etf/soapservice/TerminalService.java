package org.unibl.etf.soapservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.model.Terminal;
import org.unibl.etf.serialization.SerializationUtil;

public class TerminalService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String IDTERMINALA_TXT;
	private static String TERMINALS_DIR;
	private static String ULAZI_DIR;
	private static String IZLAZI_DIR;

	public static Terminal[] terminals;
	
	public static Handler handler;
	static {
		try {
			Path p = Paths.get("logs/centralregistry_terminal.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(TerminalService.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "CentralRegistry" + File.separator + "resources" + File.separator + "config.properties"));
			IDTERMINALA_TXT = BASE_PATH + prop.getProperty("ID_TERMINALA");
			TERMINALS_DIR = BASE_PATH + prop.getProperty("TERMINALS_DIR");
			ULAZI_DIR = BASE_PATH + prop.getProperty("ULAZI_DIR");
			IZLAZI_DIR = BASE_PATH + prop.getProperty("IZLAZI_DIR");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	private SerializationUtil serializationUtil = new SerializationUtil();
	

	public boolean createTerminal(Terminal t, boolean creatingEnterancesAndExits) {
		if(isTerminalExisting(t.getName()) != -1) { 
			return false; 
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(IDTERMINALA_TXT));
			int input = 0;
		    String line = br.readLine();
		    input  = Integer.parseInt(line);
			t.setId(input);
			  
			//kreiram terminal
			if(input %4 == 0) {
				serializationUtil.serializeWithGson(t);
			} else if(input%4==1) {
				serializationUtil.serializeWithKryo(t);
			} else if(input%4==2) {
				serializationUtil.serializeWithJava(t);
			} else {
				serializationUtil.serializeWithXML(t);
			}
			writeInputInTxtFile(input+1);
			
			//kreiram ulaze i izlaze, upisujem ih u datoteku
			if(creatingEnterancesAndExits) { 
				createEnterances(t.getNumOfEnterances(), t.getId(), 0);
				createExits(t.getNumOfExits(), t.getId(), 0);
			}
			br.close();
			return true;
		} catch (Exception e){
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
	
	
	private void writeInputInTxtFile(int input) {
		//uvecavanje inputa u datoteci
		try (FileWriter fw = new FileWriter(IDTERMINALA_TXT, false)) {
	        String count = Integer.toString(input);
	        fw.write(count);
	    } catch (IOException e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	private boolean createEnterances(int numOfEnterances, int terminalId, int i) { 
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(ULAZI_DIR + File.separator + terminalId + ".txt",  true))) {
			for(; i<numOfEnterances; i++) {
				String id = terminalId + "U" + i;
				writer.write(id);
				writer.newLine();
			}
			return true;
		}
		catch(IOException e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}
	
	private boolean createExits(int numOfExits, int terminalId, int i) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(IZLAZI_DIR + File.separator + terminalId + ".txt", true))) {
			for(; i < numOfExits; i++) {
				String id = terminalId + "I" + i;
				writer.write(id);
				writer.newLine();
			}
			return true;
		}
		catch(IOException e){
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}
	
	
	public Terminal getTerminal(int id) {
		String serializationType = "";
		if(id % 4 == 0) {
			serializationType="gson";
		} else if(id % 4 == 1) {
			serializationType="kryo";
		} else if(id % 4 == 2) {
			serializationType="java";
		} else {
			serializationType="xml";
		}
		
		//sad kad znam id i tip serijalizaciej znam i ime fajla pa cu provjeriti ima li ga
		String fileName = id + "_" +serializationType + ".out";
		File dir = new File(TERMINALS_DIR);
		File[] listOfFiles = dir.listFiles();
		boolean isFileExisting = false;
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    if(listOfFiles[i].getName().equals(fileName)) {
		    	isFileExisting = true;
		    	break;
		    }
		  }
		}
		
		//deserijalizujem fajl i vratim vrjednost
		Terminal terminal = null;
		if(!isFileExisting) {
			return terminal;
		} else {
			if(serializationType.equals("gson")) {
				terminal=serializationUtil.deserializeWithGson(id);
			} else if(serializationType.equals("kryo")) {
				terminal = serializationUtil.deserializeWithKryo(id);
			} else if(serializationType.equals("java")) {
				terminal = serializationUtil.deserializeWithJava(id);
			} else {
				terminal = serializationUtil.deserializeWithXML(id);
			}
		}
		return terminal;
	}
	
	public boolean updateTerminal(Terminal t) {
		//prvo dohvatim trenutni fajl ako postoji
		Terminal oldTerminal = getTerminal(t.getId());
		boolean successful = false;
		if(oldTerminal!=null) { //postoji taj terminal sa tim id
			if(t.getName().isEmpty()) {
				t.setName(oldTerminal.getName());
			}
			if(t.getNumOfEnterances() == oldTerminal.getNumOfEnterances() && t.getNumOfExits() == oldTerminal.getNumOfExits()) {
				//obrisem trenutni fajl o ovom terminalu i dodam novi
				deleteAfterUpdate(t.getId());
				createAfterUpdate(t);
				return true;
			}
			if(t.getNumOfEnterances() > oldTerminal.getNumOfEnterances()) {
				//znaci dodajem ulaze
				createEnterances(t.getNumOfEnterances(),t.getId(), oldTerminal.getNumOfEnterances());
			} else {
				try {
					File inputFile = new File(ULAZI_DIR + File.separator + t.getId() + ".txt");
					ArrayList<String> listOfEnterances = new ArrayList<String>();
					BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					String currentLine;
					int i=0;
					while((currentLine = reader.readLine()) != null && i < t.getNumOfEnterances() ) {
					    listOfEnterances.add(currentLine);
					    i++;
					}
					reader.close();
					
					BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
					for (String string : listOfEnterances) {
						writer.write(string);
						writer.newLine();
					}
					
					writer.close(); 
					successful=true;
				} catch (IOException e) {
					Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
			}
			
			if(t.getNumOfExits() > oldTerminal.getNumOfExits()) {
				//znaci dodajem izlaze
				createExits(t.getNumOfExits(),t.getId(), oldTerminal.getNumOfExits());
			} else {
				try {
					File inputFile = new File(IZLAZI_DIR + File.separator +t.getId()+".txt");
					ArrayList<String> listOfExits = new ArrayList<String>();
					BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					String currentLine;
					int i=0;
					while((currentLine = reader.readLine()) != null && i<t.getNumOfExits() ) {
					    listOfExits.add(currentLine);
					    i++;
					}
					reader.close();
			
					BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
					for (String string : listOfExits) {
						writer.write(string);
						writer.newLine();
					}
					writer.close(); 
					successful=true;
				} catch (IOException e) {
					Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
			}
			deleteAfterUpdate(t.getId());
			createAfterUpdate(t);
		}
		return successful;
	}
	
	
	private void createAfterUpdate(Terminal t) {
		try {
			//kreiram terminal
			if(t.getId() %4 == 0) {
				serializationUtil.serializeWithGson(t);
			} else if(t.getId()%4==1) {
				serializationUtil.serializeWithKryo(t);
			} else if(t.getId()%4==2) {
				serializationUtil.serializeWithJava(t);
			} else {
				serializationUtil.serializeWithXML(t);
			}
		} catch (Exception e){
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	private void deleteAfterUpdate(int id) {
		Terminal terminal = getTerminal(id);
		if(terminal!=null ) {
			String serilaziationType = serializationType(id);
			String fileName = TERMINALS_DIR + File.separator + id + "_" + serilaziationType + ".out";
			try {         
				File f= new File(fileName);          
				f.delete();
			}  
			catch(Exception e)  
			{  
				Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}  
		}
	}
	
	private String serializationType(int id) {
		
		String serializationType = "";
		if(id % 4 == 0) {
			serializationType="gson";
		} else if(id % 4 == 1) {
			serializationType="kryo";
		} else if(id % 4 == 2) {
			serializationType="java";
		} else {
			serializationType="xml";
		}
		return serializationType;
	}
		
	public Terminal deleteTerminal(int id) {
		Terminal terminal = getTerminal(id);
		if(terminal!=null ) {
			String serializationType = serializationType(id);
			String fileName = TERMINALS_DIR + File.separator + id + "_" + serializationType + ".out";
			try {         
				File f= new File(fileName);          
				f.delete();
				fileName = ULAZI_DIR + File.separator + id + ".txt";
				f= new File(fileName);          
				f.delete();
				fileName = IZLAZI_DIR + File.separator + id + ".txt";
				f= new File(fileName);          
				f.delete();
			}  
			catch(Exception e)  
			{  
				Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}  
		}
		return terminal;
	}
	
	
	
	public Terminal[] allTerminals() {
		String[] pathnames;
        File f = new File(TERMINALS_DIR);
        pathnames = f.list();
        int size = pathnames.length;
        terminals = new Terminal[size];
        int i =0;
        for (String pathname : pathnames) {
            Terminal terminal;
            String idString = pathname.split("_")[0];
            int id = Integer.parseInt(idString);
            if(pathname.contains("gson")) {
            	terminal = serializationUtil.deserializeWithGson(id);
            } else if(pathname.contains("kryo")) {
            	terminal = serializationUtil.deserializeWithKryo(id);
            } else if(pathname.contains("java")) {
            	terminal = serializationUtil.deserializeWithJava(id);
            } else {
            	terminal = serializationUtil.deserializeWithXML(id);
            }
            terminals[i] = terminal;
            i++;
        }
		return terminals;
	}
	
	
	public int isTerminalExisting(String name) {
		Terminal[] terminalsArray = allTerminals();
		for(int i=0; i<terminalsArray.length; i++ ) {
			if(name.equals(terminalsArray[i].getName())) {
				return terminalsArray[i].getId();
			}
		}
		return -1;
	}
	
	public boolean isEnteranceOrExitExists(String enteranceOrExitId, int terminalId) {
		boolean succesful = false;
		try {
			File f = new File(ULAZI_DIR + File.separator +terminalId+".txt");
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			while((currentLine = reader.readLine()) != null ) {
			    String trimmedLine = currentLine.trim();
			    if(enteranceOrExitId.equals(trimmedLine)) {
			    	succesful = true;
			    }
			}
			reader.close();
			
			if(succesful) {
				return succesful;
			}
			
			f = new File(IZLAZI_DIR + File.separator +terminalId+".txt");
			BufferedReader reader2 = new BufferedReader(new FileReader(f));
			while((currentLine = reader2.readLine()) != null ) {
			    String trimmedLine = currentLine.trim();
			    if(enteranceOrExitId.equals(trimmedLine)) {
			    	succesful= true;
			    }
			}
			reader2.close();
		} catch (IOException e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return succesful;
		}
		return succesful;
	}
}
