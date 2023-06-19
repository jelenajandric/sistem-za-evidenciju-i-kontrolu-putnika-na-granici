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

	private static String LOGIN_FORM;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			LOGIN_FORM = prop.getProperty("LOGIN_FORM_FXML");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	@FXML
	private TextField idTextField;
	@FXML
	private TextField typeTextField;
	@FXML
	private TextField terminalNameTextField;
	public static Stage loginStage = new Stage();
	
	private InitialFormService service;
	
	public static String enteranceOrExitId;
	public static String enteranceOrExitControlType;
	
	public InitialFormController() {
		service = new InitialFormService();
	}
	
	
	public void actionDataVerification(ActionEvent event) {
		String terminalName = terminalNameTextField.getText();
		enteranceOrExitId = idTextField.getText();
		enteranceOrExitControlType = typeTextField.getText();
		if("c".equals(enteranceOrExitControlType) || "p".equals(enteranceOrExitControlType)) {
			InitialFormResponse response = service.dataVerification(terminalName, enteranceOrExitId, enteranceOrExitControlType);
			AlertBox.display("Obavjestenje", response.getMessage());
			try {
				if(response.isIndicator()) {
		            idTextField.clear();
					terminalNameTextField.clear();
					typeTextField.clear();
					Parent p = FXMLLoader.load(getClass().getResource(LOGIN_FORM));
		            loginStage.setScene(new Scene(p));
		            loginStage.show();
				}
			} catch (IOException e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		} else {
			AlertBox.display("Greska", "Tip kontrole moze biti samo 'p' ili 'c'");
		}
	}
	
	public void addNewControlClientApp() {
		service.addNewClientControlApp(enteranceOrExitId, enteranceOrExitControlType);
	}
	
	public void closeThisControlClientApp() {
		service.closeThisClientControlApp(enteranceOrExitId, enteranceOrExitControlType);
		loginStage.close();
	}
}
