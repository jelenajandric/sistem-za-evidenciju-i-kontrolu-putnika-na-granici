package org.unibl.etf.controller;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.service.ChangePasswordService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
public class ChangePasswordController {
	
	private ChangePasswordService service;
	
	public ChangePasswordController() {
		service = new ChangePasswordService();
	}
	
	@FXML
	private PasswordField oldPasswordPField;
	@FXML
	private PasswordField newPasswordPField;
	@FXML
	private PasswordField newPasswordPField2;
	

	public void actionSaveNewPassword(ActionEvent event) {
		String oldPassword = oldPasswordPField.getText();
		String newPassword = newPasswordPField.getText();
		String newPassword2 = newPasswordPField2.getText();
		
		if(!(oldPassword.isEmpty() || newPassword.isEmpty() || newPassword2.isEmpty())) {
			if(newPassword.equals(newPassword2)) {
				if(service.validateInfo(LoginController.username, oldPassword)) {
					service.updateClient(LoginController.username, newPassword);
					MainFormController.changePasswordStage.close();
				} else {
					AlertBox.display("Greska", "Niste usnijeli ispravnu staru lozniku");
				}
			} else {
				AlertBox.display("Greska", "Nove lozinke se ne poklapaju");
			}
		} else {
			AlertBox.display("Greska", "Morate popuniti sva polja");
		}
	}
	
	
	
	

}
