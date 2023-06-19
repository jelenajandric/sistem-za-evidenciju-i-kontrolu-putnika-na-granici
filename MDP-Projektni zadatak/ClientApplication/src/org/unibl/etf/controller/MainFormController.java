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

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainFormController {
	private static final String BASE_PATH = System.getProperty("user.dir");
	
	private static String CHANGE_PASSWORD_FORM;
	private static String RECORDS_OF_CHACKED_PERSONS_FORM;
	private static String SEND_MESSAGE_FORM;
	private static String GET_ALL_MESSAGES;

	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			CHANGE_PASSWORD_FORM = prop.getProperty("CHANGE_PASSWORD_FXML");
			RECORDS_OF_CHACKED_PERSONS_FORM = prop.getProperty("RECORDS_OF_CHACKED_PERSONS_FXML");
			SEND_MESSAGE_FORM = prop.getProperty("SEND_MESSAGE_FXML");
			GET_ALL_MESSAGES = prop.getProperty("GET_ALL_MESSAGES");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	
	
	public static Stage changePasswordStage = new Stage();
	public static Stage recordsOfChackedPersonsStage = new Stage();
	public static Stage sendMessageStage = new Stage();
	public static Label label;
	
	public void actionLogout(ActionEvent event) {
		new LoginController().logout();		
		LoginController.mainFormStage.close();
		InitialFormController.loginStage.close();
	}

	public void actionChangePassword(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(CHANGE_PASSWORD_FORM));
            changePasswordStage.setScene(new Scene(p));
            changePasswordStage.show();
        } catch(IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
	}
	
	public void actionGetRecordsOfChackedPersons(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(RECORDS_OF_CHACKED_PERSONS_FORM));
            recordsOfChackedPersonsStage.setScene(new Scene(p));
            recordsOfChackedPersonsStage.show();
        } catch(IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
		
	}
	
	public void actionSendMessage(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(SEND_MESSAGE_FORM));
            sendMessageStage.setScene(new Scene(p));
            sendMessageStage.show();
        } catch(IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
		
	}
	
	public void actionGetAllMessages(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(GET_ALL_MESSAGES));
            sendMessageStage.setScene(new Scene(p));
            sendMessageStage.show();
        } catch(IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
	}

}
