package org.unibl.etf.controller;

import org.unibl.etf.service.AddNewTerminalService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddNewTerminalController {
	private AddNewTerminalService service;
	
	public AddNewTerminalController() {
		service = new AddNewTerminalService();
	}
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField numOfEnterancesTextField;
	@FXML
	private TextField numOfExitsTextField;
	
	public void saveData(ActionEvent event) {
		if(!nameTextField.getText().isEmpty() && !numOfEnterancesTextField.getText().isEmpty() && !numOfExitsTextField.getText().isEmpty()) {
			String name = nameTextField.getText();
			int numOfEnterances = Integer.parseInt(numOfEnterancesTextField.getText());
			int numOfExits = Integer.parseInt(numOfExitsTextField.getText());
			service.saveNewTerminal(numOfEnterances, numOfExits, name);
			MainFormController.addNewTerminalStage.close();
		}
	}
}
