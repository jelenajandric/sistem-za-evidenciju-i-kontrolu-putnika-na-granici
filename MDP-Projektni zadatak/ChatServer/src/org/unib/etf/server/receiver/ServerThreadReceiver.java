package org.unib.etf.server.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.server.main.MainChatServer;

public class ServerThreadReceiver extends Thread{
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerThreadReceiver(Socket socket) {
		super();
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		} catch (IOException e) {
			Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	@Override
	public void run() {
		try {
			String message = in.readLine();
			//recimo da ce poruka biti idUlaza_poruka_sender
			if(!message.isEmpty() && message!=null) {	
				MainChatServer.messagesList.add(message);
			}
			sleep(2000);
		} catch (Exception e) {
			Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
	}

}
