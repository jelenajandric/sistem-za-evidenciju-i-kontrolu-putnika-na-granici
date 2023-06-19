package org.unibl.etf.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.service.SendMessageService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class SendMessageController {
	private SendMessageService service;
	
	public SendMessageController() {
		service = new SendMessageService();
	}
	@FXML
	private TextArea messageContentTextArea;
	@FXML
	private ComboBox<String> receiverComboBox;
	
	public static final String BROADCAST = "SVI";
	public static final String MULTICAST = "SVI SA OVOG TERMINALA";
	
	@FXML
	public void initialize() {
		loadData();
	}
	
	private void loadData() {
		ArrayList<String> listaAktivnihUlaza = service.getActiveEnerancesAndExitsList(InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
		if(!listaAktivnihUlaza.isEmpty()) {
			receiverComboBox.getItems().add(BROADCAST);
			for(int i=0;i<listaAktivnihUlaza.size();i++) {
				receiverComboBox.getItems().add(listaAktivnihUlaza.get(i));
				if(!receiverComboBox.getItems().contains(MULTICAST)) {
					if(listaAktivnihUlaza.get(i).startsWith(service.getTerminalId(InitialFormController.enteranceOrExitId))) {
						receiverComboBox.getItems().add(MULTICAST);
					}
				}
			}
		}
	}
	
	public void actionSendMessage(ActionEvent event) throws IOException {
		String messageContent = messageContentTextArea.getText();
		String receiver = receiverComboBox.getValue();
		if(!(receiver.isEmpty() || messageContent.isEmpty())) {
			service.sendMessage(messageContent, receiver, InitialFormController.enteranceOrExitId+InitialFormController.enteranceOrExitControlType);
			//MainFormController.sendMessageStage.close();
		} else {
			AlertBox.display("Greska", "Morate izabrati primaoca i unijeti tekst poruke");
		}
	}
	
	
}
