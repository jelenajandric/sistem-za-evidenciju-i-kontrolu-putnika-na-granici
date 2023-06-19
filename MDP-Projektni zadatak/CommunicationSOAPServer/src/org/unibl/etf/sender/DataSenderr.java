package org.unibl.etf.sender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.communication.StartinggTestAppService;
import org.unibl.etf.communication.StartinggTestAppServiceServiceLocator;
import org.unibl.etf.model.AddingFileModel;
import org.unibl.etf.receiver.SomeStupidReceiver;

public class DataSenderr {

	private static final String POLICIJSKA="POLICIJSKA KONTROLA_";
	private static final String CARINSKA="CARINSKA KONTORLA_"; 
	private static final String DODAVANJE_FAJLOVA = "DODAVANJE FAJLOVA_";

	
	public static List<String> listaZaPolicijskuKontrolu=Collections.synchronizedList(new ArrayList<String>());
	public static List<String> listaZaCarniskuKontrolu=Collections.synchronizedList(new ArrayList<String>());
	public static List<AddingFileModel> listaZaDodavanjeFajlova = Collections.synchronizedList(new ArrayList<AddingFileModel>());

	public static Handler handler;
	static {
		try {
			Path p = Paths.get("logs/sender.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(DataSenderr.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajPodatkeZaSlanjeNaPolicijskuKontrolu(String idiTipUlazaIzlaza, int jmb, String ime, String prezime) {
		//System.out.println("Dodavanje podataka za policijsku kontrolu, dataSender klasa");
		String s=idiTipUlazaIzlaza + "_" + jmb + "#" + ime + "#" + prezime;
		listaZaPolicijskuKontrolu.add(s);
	}
	
	
	public void dodajPodatkeZaSlanjeNaCarinskuKontrolu(String idiTipUlazaIzlaza, int jmb, String ime, String prezime, boolean daLiJeNaPotjernici) {
		//System.out.println("Dodavanje podataka za carinsku kontrolu, dataSender klasa");
		String s=idiTipUlazaIzlaza + "_" + jmb + "#" + ime + "#" + prezime +"#" + daLiJeNaPotjernici;
		listaZaCarniskuKontrolu.add(s);
	}
	
	public void dodajPodatkeZaDodavanjeFajlovaa(String idiTipUlazaIzlaza, int jmb, int[] podaci, String filename) {
		//System.out.println("Dodavanje fajla, dataSender klasa " + idiTipUlazaIzlaza);
		listaZaDodavanjeFajlova.add(new AddingFileModel(idiTipUlazaIzlaza, jmb, filename, podaci));
	}

	
	public int daLiPostojiTerminal(String terminalName) {
		StartinggTestAppServiceServiceLocator locator = new StartinggTestAppServiceServiceLocator();
		StartinggTestAppService service;
		try {
			service = locator.getStartinggTestAppService();
			int terminalId = service.isTerminalExist(terminalName);
			return terminalId;
		} catch (Exception e) {
			Logger.getLogger(DataSenderr.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return -1;
		}
	}
	
	public boolean daLiPostojiAktivanUlazIzlaz(String idUlazaIzlaza, int idTerminala) {
		StartinggTestAppServiceServiceLocator locator = new StartinggTestAppServiceServiceLocator();
		StartinggTestAppService service;
		try {
			service = locator.getStartinggTestAppService();
			boolean daLiPostoji = service.isEnteranceOrExitExistAndActive(idUlazaIzlaza, idTerminala);
			return daLiPostoji;
		} catch (Exception e) {
			Logger.getLogger(DataSenderr.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
	}
	
	public String imaLiStaZaTestnuAplikaciju(int jmb) {
		for(int i=0; i<SomeStupidReceiver.odgovoriPolicijskeKontrole.size(); i++) {
			//System.out.println("odgovoriPoloicijskeKontrole jmb = " + SomeStupidReceiver.odgovoriPolicijskeKontrole.get(i).split("_")[0]);
			//System.out.println("odgovor " + SomeStupidReceiver.odgovoriPolicijskeKontrole.get(i).split("_")[1]);
			String jmbkojiJeDosaoString= SomeStupidReceiver.odgovoriPolicijskeKontrole.get(i).split("_")[0];
			int jmbKojiJeDosaInt = Integer.parseInt(jmbkojiJeDosaoString);
			if(jmbKojiJeDosaInt == jmb) {
				String odgovor=SomeStupidReceiver.odgovoriPolicijskeKontrole.get(i).split("_")[1];
				SomeStupidReceiver.odgovoriPolicijskeKontrole.remove(i);
				i--;
				return POLICIJSKA + odgovor;
			}
		}
		
		for(int i=0; i<SomeStupidReceiver.odgovoriCarinskeKontrole.size(); i++) {
			//System.out.println("odgovoriCarinskeKontrole sizee = " + SomeStupidReceiver.odgovoriCarinskeKontrole.size());
			String jmbkojiJeDosaoString= SomeStupidReceiver.odgovoriCarinskeKontrole.get(i).split("_")[0];
			int jmbKojiJeDosaInt = Integer.parseInt(jmbkojiJeDosaoString);
			if(jmbKojiJeDosaInt == jmb) {
				String odgovor=SomeStupidReceiver.odgovoriCarinskeKontrole.get(i).split("_")[1];
				//System.out.println("ODGOVOR " + odgovor);
				SomeStupidReceiver.odgovoriCarinskeKontrole.remove(i);
				i--;
				return CARINSKA + odgovor;
			}
		}
		for(int i=0; i<SomeStupidReceiver.odgovoriDodavanjaFajla.size(); i++) {
			//System.out.println("Ima li ista za testnu od dodavanja fajla");
			String jmbkojiJeDosaoString= SomeStupidReceiver.odgovoriDodavanjaFajla.get(i).split("_")[0];
			int jmbKojiJeDosaInt = Integer.parseInt(jmbkojiJeDosaoString);
			if(jmbKojiJeDosaInt == jmb) {
				String odgovor=SomeStupidReceiver.odgovoriDodavanjaFajla.get(i).split("_")[1];
				//System.out.println("ODGOVOR " + odgovor);
				SomeStupidReceiver.odgovoriDodavanjaFajla.remove(i);
				i--;
				return DODAVANJE_FAJLOVA + odgovor;
			}
		}
		for(int i=0; i<SomeStupidReceiver.odgovoriKadJeObustavaSaobracaja.size(); i++) {
			//System.out.println("Ima li ista za testnu od obustave saobracja");
			String jmbkojiJeDosaoString= SomeStupidReceiver.odgovoriKadJeObustavaSaobracaja.get(i).split("_")[0];
			int jmbKojiJeDosaInt = Integer.parseInt(jmbkojiJeDosaoString);
			if(jmbKojiJeDosaInt == jmb) {
				String odgovor=SomeStupidReceiver.odgovoriKadJeObustavaSaobracaja.get(i).split("_")[1];
				//System.out.println("ODGOVOR " + odgovor);
				String idUlazaIzlaza = SomeStupidReceiver.odgovoriKadJeObustavaSaobracaja.get(i).split("_")[2];
				SomeStupidReceiver.odgovoriKadJeObustavaSaobracaja.remove(i);
				i--;
				return odgovor + " na terminalu " +  idUlazaIzlaza;
			}
		}
		
		return "";
	}
}
