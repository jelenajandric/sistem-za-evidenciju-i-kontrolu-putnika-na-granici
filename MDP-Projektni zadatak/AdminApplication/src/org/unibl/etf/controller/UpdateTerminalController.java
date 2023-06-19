package org.unibl.etf.controller;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.service.UpdateTerminalService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateTerminalController {
	private UpdateTerminalService service;
	
	public UpdateTerminalController() {
		service = new UpdateTerminalService();
	}
	@FXML
	private TextField terminalIdTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField numOfEnterancesTextField;
	@FXML
	private TextField numOfExitsTextField;
	
	public void actionSaveChanges(ActionEvent event) {
		if(!terminalIdTextField.getText().isEmpty()) {
			int terminalId = Integer.parseInt(terminalIdTextField.getText());
			String name = nameTextField.getText();
			int numOfEnterances = Integer.parseInt(numOfEnterancesTextField.getText());
			int numOfExits = Integer.parseInt(numOfExitsTextField.getText());
			service.saveChanges(terminalId, numOfEnterances, numOfExits, name);
			MainFormController.updateTerminalStage.close();
		} else {
			AlertBox.display("Obavjestenje", "Morate unijeti id");
		}
	}

}
