package org.unibl.etf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.main.Main;
import org.unibl.etf.service.AddPersonalDataService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sun.net.www.content.text.plain;

public class AddPersonalDataController {

	private AddPersonalDataService service;
	
	public AddPersonalDataController() {
		service = new AddPersonalDataService();
	}
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField surnameTextField;
	@FXML
	private TextField jmbTextField;
	@FXML
	private Button addFileButton;
	
	public static String name;
	public static String surname;
	public static int jmb;
	
	public void actionSaveData(ActionEvent event) {
		 name = nameTextField.getText();
		 surname = surnameTextField.getText();
		 jmb = Integer.parseInt(jmbTextField.getText());
		if(!(jmbTextField.getText().isEmpty() || name.isEmpty() || surname.isEmpty())) {
			service.sendDataOnServerForPoliceControl(InitialFormController.enteranceOrExitId, jmb, name, surname);
		} else {
			AlertBox.display("Greska", "Morate unijeti sva polja");
		}
	}
	
	public void actionAddFile(ActionEvent event) {
		if(!(jmbTextField.getText().isEmpty()) && !(nameTextField.getText().isEmpty()) && !(surnameTextField.getText().isEmpty())) {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("all files *.*", "*.*");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showOpenDialog(InitialFormController.addPersonalDataStage);
			//System.out.println("filenmae === " + file.getName());
			ArrayList<Integer> procitaniBajtovi = new ArrayList<Integer>();
			try {
				InputStream inputStream = new FileInputStream(file);
	            int byteRead = -1;
	            while ((byteRead = inputStream.read()) != -1) {
	                procitaniBajtovi.add(byteRead);
	            }
	            inputStream.close();
	            Integer[] nizProcitanihNajtova = new Integer[procitaniBajtovi.size()];
	            for(int i=0;i<procitaniBajtovi.size();i++) {
	            	nizProcitanihNajtova[i] = procitaniBajtovi.get(i);
	            }
	            int[] vec1 =  procitaniBajtovi.stream().filter(t -> t != null).mapToInt(t -> t).toArray();
	            service.saveFile(vec1, Integer.parseInt(jmbTextField.getText()), file.getName(), InitialFormController.enteranceOrExitId);			
			} catch (Exception e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		} else {
			AlertBox.display("Obavjestenje", "Morate unijeti sva polja i proci policijsku kontrolu da biste dodali fajl");
		}
	}
}
