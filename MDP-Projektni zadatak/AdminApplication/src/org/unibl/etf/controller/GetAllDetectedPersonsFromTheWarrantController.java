package org.unibl.etf.controller;

import java.util.ArrayList;
import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.model.Person;
import org.unibl.etf.service.GetAllDetectedPersonsFromTheWarrantService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetAllDetectedPersonsFromTheWarrantController {
	private GetAllDetectedPersonsFromTheWarrantService service;
	
	public GetAllDetectedPersonsFromTheWarrantController() {
		service = new GetAllDetectedPersonsFromTheWarrantService();
	}
	
	@FXML
	private TableColumn<Person, Integer> jmbColumn;
	@FXML
	private TableColumn<Person, String> nameColumn;
	@FXML
	private TableColumn<Person, String> surnameColumn;
	@FXML
	private TableView<Person> allDetectedPersonsFromTheWarrantTable;
	@FXML
	private TextField jmbTextField;
	
	@FXML
    public void initialize() {
        loadData();
    }

    public void loadData() {
        loadTable();
        jmbColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("jmbg"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
    }

    private void loadTable() {
    	allDetectedPersonsFromTheWarrantTable.getItems().clear();
    	ArrayList<Person> persons = service.getAndSaveDataAboutDetectedPersonsFromTheWarrantFromCentralRegistry();
    	if(!persons.isEmpty() && persons!=null) {
    		allDetectedPersonsFromTheWarrantTable.setItems(FXCollections.observableList(persons));
    	} else {
    		AlertBox.display("Obavjestenje", "Ova tabela je prazna, nema detektovanih osoba sa potjernice.");
    	}
    }
}
