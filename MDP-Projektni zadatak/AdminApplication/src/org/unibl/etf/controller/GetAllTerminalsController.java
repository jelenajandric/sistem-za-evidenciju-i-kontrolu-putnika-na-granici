package org.unibl.etf.controller;

import java.util.List;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.model.Terminal;
import org.unibl.etf.service.GetAllTerminalsService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetAllTerminalsController {
	private GetAllTerminalsService service;

	public GetAllTerminalsController() {
		super();
		service = new GetAllTerminalsService();
	}
	@FXML
	private TableColumn<Terminal, Integer> idColumn;
	@FXML
	private TableColumn<Terminal, String> nameColumn;
	@FXML
	private TableColumn<Terminal, Integer> numOfEnterancesColumn;
	@FXML
	private TableColumn<Terminal, Integer> numOfExitsColumn;
	@FXML
	private TableView<Terminal> allTerminalsTableView;
	
	@FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        loadTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<Terminal, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numOfEnterancesColumn.setCellValueFactory(new PropertyValueFactory<>("numOfEnterances"));
        numOfExitsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfExits"));
    }

    private void loadTable() {
        try {
        	allTerminalsTableView.getItems().clear();
        	List<Terminal> terminals = service.getAllTerminals();
        	if(terminals!=null && !terminals.isEmpty()) {
        		allTerminalsTableView.setItems(FXCollections.observableList(terminals));
        	} else {
        		AlertBox.display("Obavjestenje", "Ova tabela je prazna, ne postoji ni jedan terminal.");
        	}
        } catch (Exception ex) {
            AlertBox.display("Obavjestenje", "Doslo je do greske pri prikazu");
        }
    }

}
