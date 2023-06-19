package org.unibl.etf.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main  extends Application {
	private static final String BASE_PATH = System.getProperty("user.dir");

	Scene scene;
	
	public static Handler handler;
	static {
		try {
			Path p = Paths.get("logs/adminapp.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(Main.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String MAIN_FORM_FXML;
	static {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			MAIN_FORM_FXML = prop.getProperty("MAIN_FORM_FXML");
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public static void main(String[] args) {
		//System.out.println(BASE_PATH);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			//System.out.println(System.getProperty("user.dir"));
			root = FXMLLoader.load(getClass().getResource(MAIN_FORM_FXML));
	        primaryStage.setTitle("Adiministratorska aplikacija");
	        scene= new Scene(root);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
}
