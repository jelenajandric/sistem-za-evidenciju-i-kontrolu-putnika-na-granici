package org.unibl.etf.communication;

import org.unibl.etf.controls.customscontrol.CustomsControl;
import org.unibl.etf.controls.policecontrol.PoliceControl;
import org.unibl.etf.model.DataAnalyzationResponse;

public class DataAnalizationUtil {

	private static final String POLICIJSKA="POLICIJSKA KONTROLA_";
	private static final String CARINSKA="CARINSKA KONTORLA_"; 
	private static final String DODAVANJE_FAJLOVA = "DODAVANJE FAJLOVA_";

	
	public DataAnalyzationResponse analyzeData(String data) {
		if(data!=null) {
			if(data.startsWith(POLICIJSKA)) {
				
				int jmb = Integer.parseInt(data.split("_")[1]);
				String name = data.split("_")[2];
				String surname = data.split("_")[3];
				boolean result = new PoliceControl().daLiProlaziPolicijskuKontrolu(jmb, name, surname);
				return new DataAnalyzationResponse(result,jmb,POLICIJSKA);
				
			} else if(data.startsWith(CARINSKA) ) {
				
				int jmb = Integer.parseInt(data.split("_")[1]);
				String name = data.split("_")[2];
				String surname = data.split("_")[3];
				boolean isItOnTheWantedList = Boolean.parseBoolean(data.split("_")[4]);
				boolean result= new CustomsControl().addDataAboutPerson(jmb, name, surname, isItOnTheWantedList);
				return new DataAnalyzationResponse(!result,jmb,CARINSKA);
				
			} else if(data.startsWith(DODAVANJE_FAJLOVA)) {
				
				int jmb = Integer.parseInt(data.split("_")[1]);
				String fileName = data.split("_")[2];
				String podaciString = data.split("_")[3];
				podaciString = podaciString.substring(1, podaciString.length()-1);
				String[] splitArray = podaciString.split(", ");
		        Integer[] array = new Integer[splitArray.length];
		        for (int i = 0; i < splitArray.length; i++) {
		            array[i] = Integer.parseInt(splitArray[i]);
		        }
		        boolean result = new CustomsControl().saveFile(array, jmb, fileName);
				return new DataAnalyzationResponse(result, jmb, DODAVANJE_FAJLOVA);
 			}
		}
		return new DataAnalyzationResponse();
	}
}
