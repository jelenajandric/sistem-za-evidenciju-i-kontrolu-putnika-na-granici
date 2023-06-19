package org.unibl.etf.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.main.Main;
import org.unibl.etf.model.Person;
import org.unibl.etf.service.RecordsOfChackedPersonsService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecordsOfChackedPersonsController {
	@FXML
	private TableColumn<Person, Integer> jmbColumn;
	@FXML
	private TableColumn<Person, String> nameColumn;
	@FXML
	private TableColumn<Person, String> surnameColumn;
	@FXML
	private TableView<Person> recordsOfChackedPersonsTableView;
	
	private RecordsOfChackedPersonsService service;
	
	public RecordsOfChackedPersonsController() {
		service = new RecordsOfChackedPersonsService();
	}
	
	@FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        loadTable();
        jmbColumn.setCellValueFactory(new PropertyValueFactory<>("jmbg"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
    }

    private void loadTable() {
        try {
        	recordsOfChackedPersonsTableView.getItems().clear();
        	recordsOfChackedPersonsTableView.setItems(FXCollections.observableList(service.getRecordsOfChackedPersons()));
        } catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            AlertBox.display("Obavjestenje", "Doslo je do greske pri prikazu");
        }
    }
}
