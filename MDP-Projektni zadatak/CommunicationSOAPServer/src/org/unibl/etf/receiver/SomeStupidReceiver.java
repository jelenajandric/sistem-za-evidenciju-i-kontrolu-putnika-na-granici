package org.unibl.etf.receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.unibl.etf.sender.DataSenderr;

public class SomeStupidReceiver {

	public static List<String> odgovoriPolicijskeKontrole=Collections.synchronizedList(new ArrayList<String>());
	public static List<String> odgovoriCarinskeKontrole=Collections.synchronizedList(new ArrayList<String>());
	public static List<String> odgovoriDodavanjaFajla = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> odgovoriKadJeObustavaSaobracaja = Collections.synchronizedList(new ArrayList<String>());
	
	private static final String POLICIJSKA="POLICIJSKA KONTROLA_";
	private static final String CARINSKA="CARINSKA KONTORLA_"; 
	private static final String DODAVANJE_FAJLOVA = "DODAVANJE FAJLOVA_";
	
	public void dodajOdgovorOdPolicijskeKontrole(int jmb, String idUlazaIzlaza, boolean daLiProlaziPolicijskuKontrolu) {
		//System.out.println("dodajOdgovorOdPolicijskeKontrole");
		String porukaString = jmb+ "_" + daLiProlaziPolicijskuKontrolu + "_" + idUlazaIzlaza;
		odgovoriPolicijskeKontrole.add(porukaString);
	}
	public void dodajOdgovorOdCarinskeKontrole(int jmb, String idUlazaIzlaza, boolean daLiProlaziCarinskuKontrolu) {
		//System.out.println("dodajOdgovorOdCarinskeKontrole");
		String porukaString = jmb+ "_" + daLiProlaziCarinskuKontrolu + "_" + idUlazaIzlaza;
		odgovoriCarinskeKontrole.add(porukaString);
	}
	
	public void dodajOdgovorOdDodavanjaFajlaa(int jmb, String idUlazaIzlaza, boolean daLiJeDodatFajl) {
		//System.out.println("dodajOdgovorOdDodavanjaFajla");
		String porukaString = jmb+ "_" + daLiJeDodatFajl + "_" + idUlazaIzlaza;
		odgovoriDodavanjaFajla.add(porukaString);
	}
	
	public void dodajOdgovorKadJeObustavaSaobracaja(int jmb, String idUlazaIzlaza, String poruka)  {
		//System.out.println("dodajOdgovorKadJeObustavaSaobracaja");
		String porukaString = jmb+ "_" + poruka + "_" + idUlazaIzlaza;
		odgovoriKadJeObustavaSaobracaja.add(porukaString);
	}

	
	public String imaLiStaZaPolicijskuKontrolu(String idITipUlazaIzlaza) {
		for(int i=0; i<DataSenderr.listaZaPolicijskuKontrolu.size(); i++) {
			if(DataSenderr.listaZaPolicijskuKontrolu.get(i).split("_")[0].equals(idITipUlazaIzlaza)) {
				String koristanSadrzaj=DataSenderr.listaZaPolicijskuKontrolu.get(i).split("_")[1];
				if(!koristanSadrzaj.isEmpty()) {
					int jmb = Integer.parseInt(koristanSadrzaj.split("#")[0]);
					String ime = koristanSadrzaj.split("#")[1];
					String prezime = koristanSadrzaj.split("#")[2];
					DataSenderr.listaZaPolicijskuKontrolu.remove(i);
					i--;
					return POLICIJSKA + jmb+ "_" + ime + "_" + prezime;
				}
			}
		}
		return "";
	}
	
	
	public String imaLiStaZaCarinskuKontrolu(String idITipUlazaIzlaza) {
		for(int i=0; i<DataSenderr.listaZaCarniskuKontrolu.size(); i++) {
			if(DataSenderr.listaZaCarniskuKontrolu.get(i).split("_")[0].equals(idITipUlazaIzlaza)) {
				String koristanSadrzaj=DataSenderr.listaZaCarniskuKontrolu.get(i).split("_")[1];
				if(!koristanSadrzaj.isEmpty()) {
					String jmb = koristanSadrzaj.split("#")[0];
					String ime = koristanSadrzaj.split("#")[1];
					String prezime = koristanSadrzaj.split("#")[2];
					boolean daLiJeNaPOtjernici = Boolean.parseBoolean(koristanSadrzaj.split("#")[3]);
					DataSenderr.listaZaCarniskuKontrolu.remove(i);
					i--;
					return CARINSKA + jmb+ "_" + ime + "_" + prezime + "_" + daLiJeNaPOtjernici;
				}
			}
		}
		for(int i=0; i<DataSenderr.listaZaDodavanjeFajlova.size();i++) {
			if(DataSenderr.listaZaDodavanjeFajlova.get(i).getEntranceOrExitId().equals(idITipUlazaIzlaza)) {
				int jmb = DataSenderr.listaZaDodavanjeFajlova.get(i).getJmb();
				String filename = DataSenderr.listaZaDodavanjeFajlova.get(i).getFilename();
				String podaci = Arrays.toString(DataSenderr.listaZaDodavanjeFajlova.get(i).getFileData());
				//System.out.println(jmb + "  " + filename + "  " + podaci);
				DataSenderr.listaZaDodavanjeFajlova.remove(i);
				i--;
				return DODAVANJE_FAJLOVA + jmb + "_" + filename + "_" + podaci;
			}
		}
		return "";
	}
	
	
}
