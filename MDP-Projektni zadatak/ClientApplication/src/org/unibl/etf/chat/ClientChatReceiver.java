package org.unibl.etf.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.unibl.etf.alert.AlertBox;
import org.unibl.etf.communication.Komunikacija;

import javafx.application.Platform;

public class ClientChatReceiver extends Thread {
	private static final String BASE_PATH = System.getProperty("user.dir");	
	
	private static ChatService chatService;
	
	private static String HOST;
	private static int PORT;
	private static String KEY_STORE_PATH;
	private static String KEY_STORE_PASSWORD;
	
	public ClientChatReceiver() {
		chatService = new ChatService();
	}
	
	{
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			HOST = prop.getProperty("HOST");
			PORT = Integer.parseInt(prop.getProperty("RECEIVER_PORT"));
			KEY_STORE_PATH = BASE_PATH + prop.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = prop.getProperty("KEY_STORE_PASSWORD");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}

	private static final String OBUSTAVA_SAOBRACAJA_PORUKA = "OBUSTAVA SAOBRACAJA";
	private static final String NASTAVAK_SAOBRACAJA_PORUKA = "NASTAVAK SAOBRACAJA";
	
	private String enteranceOrExitId;
	private String enteranceOrExitControlType;
	
	@Override
	public void run() {
		System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);

		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket s;
		BufferedReader in;
		PrintWriter out;
		try {
			s = (SSLSocket) sf.createSocket(HOST, PORT);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

			while(true) {
				out.println(enteranceOrExitId+enteranceOrExitControlType);
				String poruka= in.readLine();
				if(!poruka.isEmpty()) {
					//System.out.println("primljeno " + poruka);
					String receiver = poruka.split("_")[0];
					String content = poruka.split("_")[1];
					String sender = poruka.split("_")[2];
	                //chatService.saveMessageIntoFile(receiver, content, sender);
					Runnable task = () -> {
			            Platform.runLater(() -> {
			                AlertBox.display("Poruka za " + receiver, "Od: " + sender +"\n " + content);
			                chatService.saveMessageIntoFile(receiver, content, sender);
			            });
			        };
			        Thread thread = new Thread(task);
			        thread.setDaemon(true);
			        thread.start();
			        if(poruka.contains(OBUSTAVA_SAOBRACAJA_PORUKA)) {
			        	Komunikacija.OBUSTAVA_SAOBRACAJA = true;
			        } else if(poruka.contains(NASTAVAK_SAOBRACAJA_PORUKA)) {
			        	Komunikacija.OBUSTAVA_SAOBRACAJA = false;
			        }
				}
				sleep(2000);
			} 
		} catch (Exception e) {
			Logger.getLogger(ChatService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public void setValues(String enteranceOrExitId, String enteranceOrExitControlType) {
		this.enteranceOrExitId = enteranceOrExitId;
		this.enteranceOrExitControlType = enteranceOrExitControlType;
	}
} 
