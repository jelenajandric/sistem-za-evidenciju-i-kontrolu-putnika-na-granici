package org.unibl.etf.controller;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.main.Main;
import org.unibl.etf.model.ClientMessage;
import org.unibl.etf.service.GetAllMessagesService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetAllMessagesController {

	@FXML
	private TableView<ClientMessage> allMessagesTableView;
	@FXML
	private TableColumn<ClientMessage, String> senderColumn;
	@FXML
	private TableColumn<ClientMessage, String> contentColumn;
	@FXML
	private TableColumn<ClientMessage, String> dateAndTimeColumn;
	
	private GetAllMessagesService getAllMessagesService;
	
	public GetAllMessagesController() {
		getAllMessagesService = new GetAllMessagesService();
	}
	
	@FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        loadTable();
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
    }

    private void loadTable() {
        try {
        	allMessagesTableView.getItems().clear();
        	ArrayList<ClientMessage> messages = getAllMessagesService.getAllMessages(InitialFormController.enteranceOrExitId + InitialFormController.enteranceOrExitControlType);
        	if(messages!=null && !messages.isEmpty()) {
        		allMessagesTableView.setItems(FXCollections.observableList(messages));
        	} else {
        		AlertBox.display("Obavjestenje", "Nemate ni jednu poruku");
        	}
        } catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("Obavjestenje", "Doslo je do greske pri prikazu");
        }
    }

}
