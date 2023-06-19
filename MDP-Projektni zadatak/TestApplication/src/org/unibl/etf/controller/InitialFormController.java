package org.unibl.etf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.main.Main;
import org.unibl.etf.model.InitialFormResponse;
import org.unibl.etf.service.InitialFormService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitialFormController {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String ADD_PERSONAL_DATA_FORM;
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			ADD_PERSONAL_DATA_FORM = prop.getProperty("ADD_PEROSNAL_DATA_FXML");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	@FXML
	private TextField terminalNameTextField;
	@FXML
	private TextField enteranceOrExitIdTextField;
	
	public static String enteranceOrExitId;
	
	private InitialFormService service;
	
	public InitialFormController() {
		service = new InitialFormService();
	}

	
	public static Stage addPersonalDataStage = new Stage();
	
	public void actionDataVerification(ActionEvent event) {
		String terminalName = terminalNameTextField.getText();
		enteranceOrExitId = enteranceOrExitIdTextField.getText();
		InitialFormResponse response = service.verifieData(terminalName, enteranceOrExitId);
		AlertBox.display("Obavjestenje", response.getMessage());
		if(response.isIndicator()) {
			Parent p;
			try {
				p = FXMLLoader.load(getClass().getResource(ADD_PERSONAL_DATA_FORM));
				addPersonalDataStage.setScene(new Scene(p));
				addPersonalDataStage.show();
			} catch (IOException e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
	}	
}
