package org.unibl.etf.controller;

import java.util.ArrayList;
import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.service.GetAllClientAccountsService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class GetAllClientAccountsController {
	private GetAllClientAccountsService service;
	
	@FXML
	private ListView<String> usernamesListView;
	
	public GetAllClientAccountsController() {
		service = new GetAllClientAccountsService();
	}
	
	@FXML
    public void initialize() {
        loadList();
    }

    private void loadList() {
      	usernamesListView.getItems().clear();
    	ArrayList<String> clients = service.getAllClientAccounts();
    	if(!clients.isEmpty()) {
    		usernamesListView.setItems(FXCollections.observableList(clients));
    	} else {
    		AlertBox.display("Obavjestenje", "Ova lista je prazna, ne postoji ni jedan klijentski nalog.");
    	}
    }
}
