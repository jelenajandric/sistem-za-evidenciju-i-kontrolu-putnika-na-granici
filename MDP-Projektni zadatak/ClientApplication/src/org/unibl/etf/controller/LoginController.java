package org.unibl.etf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.chat.ClientChatReceiver;
import org.unibl.etf.communication.Komunikacija;
import org.unibl.etf.main.Main;
import org.unibl.etf.service.LoginService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String MAIN_FORM;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			MAIN_FORM = prop.getProperty("MAIN_FORM_FXML");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	private LoginService service;
	private static ClientChatReceiver chatReceiver;
	private static Komunikacija komunikacija;
	
	public LoginController() {
		service = new LoginService();
	}
	public static Stage mainFormStage = new Stage();

	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	
	public static String username;
	
	public void actionLogin(ActionEvent event) {
		username = usernameTextField.getText();
		String lozinka = passwordTextField.getText();
		if(!username.isEmpty() && !lozinka.isEmpty()) {
			if(service.validateInfo(username, lozinka)) {
				if(!service.isClientAlreadyLogin(username)) {
					//stavi username u username_app_matching
					service.saveClientToUsernameAppMatching(username, InitialFormController.enteranceOrExitId + InitialFormController.enteranceOrExitControlType);
					chatReceiver = new ClientChatReceiver();
					chatReceiver.setValues(InitialFormController.enteranceOrExitId, InitialFormController.enteranceOrExitControlType);
					chatReceiver.start();
					komunikacija = new Komunikacija();
					komunikacija.start();
					//new KlijentReceiver(enteranceOrExitId).start();
					new InitialFormController().addNewControlClientApp();
					try {
						Parent p = FXMLLoader.load(getClass().getResource(MAIN_FORM));
						InitialFormController.loginStage.close();
				        mainFormStage.setScene(new Scene(p));
				        mainFormStage.setTitle(username + ":" + InitialFormController.enteranceOrExitId + InitialFormController.enteranceOrExitControlType);
				        mainFormStage.show();
					} catch(IOException e) {
						Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
				} else {
					AlertBox.display("Greska", "Klijent je vec prijavljen pod ovim nalogom");
				}
			} else {
				AlertBox.display("Greska", "Ovaj nalog ne postoji");
			}
		} else {
			AlertBox.display("Greska", "Morate unijeti podatke");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void logout() {
		chatReceiver.stop();
		komunikacija.stop();
		service.removeClientFromUsernameAppMatching(username, InitialFormController.enteranceOrExitId + InitialFormController.enteranceOrExitControlType);
		new InitialFormController().closeThisControlClientApp();
		mainFormStage.close();
	}
}
