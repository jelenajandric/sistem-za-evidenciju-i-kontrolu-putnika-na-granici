package org.unibl.etf.server.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.unib.etf.server.receiver.ServerReceiver;
import org.unibl.etf.server.sender.ServerSender;

public class MainChatServer {
	
	public static Handler handler;
	static {
		try {
			Path p = Paths.get("logs/chatserver.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(MainChatServer.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List<String> messagesList = Collections.synchronizedList(new ArrayList<String>());

	public static void main(String[] args) throws IOException {		
		new Thread() {
			@Override
			public void run() { 
				new ServerReceiver();
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() { 
				new ServerSender();
			}
		}.start();
	}
}
