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
import org.unibl.etf.service.DeleteClientAccountService;
import org.unibl.etf.service.DeleteTerminalService;
import org.unibl.etf.service.SavePersonsDocsService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainFormController {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private DeleteTerminalService deleteTerminalService;
	private DeleteClientAccountService deleteClientAccountService;
	private SavePersonsDocsService savePersonsDocsService;
	
	public MainFormController() {
		deleteTerminalService = new DeleteTerminalService();
		deleteClientAccountService = new DeleteClientAccountService();
		savePersonsDocsService = new SavePersonsDocsService();
	}
	
	
	private static String ALL_TERMINALS_FXML;
	private static String ADD_NEW_TERMINAL_FXML;
	private static String UPDATE_TERMINAL_FXML;
	private static String ADD_NEW_CLIENT_ACCOUNT_FXML;
	private static String ALL_CLIENTS_FXML;
	private static String ALL_DETECTED_PERSONS_FROM_WARRANT;
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			ALL_TERMINALS_FXML = prop.getProperty("ALL_TERMINALS_FXML");
			ADD_NEW_TERMINAL_FXML =prop.getProperty("ADD_NEW_TERMINAL_FXML");
			UPDATE_TERMINAL_FXML = prop.getProperty("UPDATE_TERMINAL_FXML");
			ADD_NEW_CLIENT_ACCOUNT_FXML = prop.getProperty("ADD_NEW_CLIENT_ACCOUNT_FXML");
			ALL_CLIENTS_FXML = prop.getProperty("ALL_CLIENTS_FXML");
			ALL_DETECTED_PERSONS_FROM_WARRANT = prop.getProperty("ALL_DETECTED_PERSONS_FROM_WARRANT");
		} catch (FileNotFoundException e1) {
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public static Stage allTerminalsStage = new Stage();
	public static Stage addNewTerminalStage = new Stage();
	public static Stage updateTerminalStage = new Stage();	
	public static Stage addNewClientAccountStage = new Stage();
	public static Stage allClientAccountsStage = new Stage();
	public static Stage AllDetectedPersonsFromTheWarrantStage = new Stage();
	
	@FXML
	private TextField terminalIdTextField;
	@FXML
	private TextField usernameTextField;
	
	public void actionGetAllTerminals(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(ALL_TERMINALS_FXML));
            allTerminalsStage.setScene(new Scene(p));
            allTerminalsStage.show();
        } catch(IOException ex) {
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());

        }
	}
	
	public void actionAddNewTerminal(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(ADD_NEW_TERMINAL_FXML));
            addNewTerminalStage.setScene(new Scene(p));
            addNewTerminalStage.show();
        } catch(IOException ex) {
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
	}

	
	public void actionUpdateTerminal(ActionEvent event) {
		try {
	        Parent p = FXMLLoader.load(getClass().getResource(UPDATE_TERMINAL_FXML));
	        updateTerminalStage.setScene(new Scene(p));
	        updateTerminalStage.show();
        } catch(IOException ex) {
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
	}
	
	public void actionDeleteTerminal(ActionEvent event) {
		if(terminalIdTextField.getText().isEmpty()) {
			AlertBox.display("Greska", "Morate unijeti id");
		} else {
			deleteTerminalService.deleteTerminal(Integer.parseInt(terminalIdTextField.getText()));
		}
	}
	
	public void actionAddNewClientAccount(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(ADD_NEW_CLIENT_ACCOUNT_FXML));
            addNewClientAccountStage.setScene(new Scene(p));
            addNewClientAccountStage.show();
        } catch(IOException ex) {
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
	}
	
	public void actionGetAllClientAccounts(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(ALL_CLIENTS_FXML));
            allClientAccountsStage.setScene(new Scene(p));
            allClientAccountsStage.show();
        } catch(IOException ex) {
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
		
		
	}

	public void actionDeleteClientAccount(ActionEvent event) {
		if(usernameTextField.getText().isEmpty()) {
			AlertBox.display("Greska", "Morate unijeti korisnicko ime");
		} else {
			deleteClientAccountService.deleteClientAccount(usernameTextField.getText());
		}
	}
	

	public void actionGetAllDetectedPersonsFromTheWarrant(ActionEvent event) {
		try {
            Parent p = FXMLLoader.load(getClass().getResource(ALL_DETECTED_PERSONS_FROM_WARRANT));
            AllDetectedPersonsFromTheWarrantStage.setScene(new Scene(p));
            AllDetectedPersonsFromTheWarrantStage.show();
        } catch(IOException ex) {
        	Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("GRESKA","Greska pri ucitavanju fxml");
        }
	}
	
	public void actionSavePersonsDocs(ActionEvent event) {
		if(savePersonsDocsService.savePersonsDocsFromFileServer()) {
			AlertBox.display("Obavjestenje","Uspjesno su preuzeti fajlovi");
		} else {
			AlertBox.display("Obavjestenje","Doslo je do greske, fajlovi nisu preuzeti");
		}
	}
}
