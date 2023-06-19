package org.unibl.etf.controller;

import java.util.ArrayList;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.service.AddNewClientAccountService;
import org.unibl.etf.service.GetAllClientAccountsService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewClientAccountController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	
	private AddNewClientAccountService service;
	
	public AddNewClientAccountController() {
		service = new AddNewClientAccountService();
	}
	
	public void actionSaveData(ActionEvent event) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		if(!username.isEmpty() && !password.isEmpty()) {
			boolean indicator = false;
			ArrayList<String> clients = new GetAllClientAccountsService().getAllClientAccounts();
			for(String s:clients) {
				if(s.equals(username)) {
					indicator=true;
					AlertBox.display("Obavjestenje", "Nalog sa tim korisnickim imenom vec postoji.");
					break;
				}
			}
			if(!indicator) {
				service.createClient("{\"username\":\"" + username +"\",\"password\":\"" + password +"\"}");
				MainFormController.addNewClientAccountStage.close();
			}
		}
	}
}
