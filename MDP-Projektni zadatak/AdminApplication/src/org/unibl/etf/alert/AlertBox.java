package org.unibl.etf.alert;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String text) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        Label label = new Label();
        label.setText(text);
        Button close = new Button("Zatvori");
        close.setOnAction(e -> stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.getChildren().add(close);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
