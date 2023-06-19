package org.unibl.etf.server.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.server.main.MainChatServer;

public class ServerThreadSender extends Thread{

	
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	public ServerThreadSender(Socket socket) {
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
		String id="";
		while(true) {
			try {
				if(id.isEmpty()) {
					id=in.readLine();
				}
				if(!MainChatServer.messagesList.isEmpty()) {
					for(int i=0;i<MainChatServer.messagesList.size();i++) {
						String text=MainChatServer.messagesList.get(i);
						String receiver = text.split("_")[0];
						if(receiver.equals(id)) {
							//System.out.println("receiver  "  + receiver);
							//System.out.println("mesagee list size " + MainChatServer.messagesList.size());
							MainChatServer.messagesList.remove(i);
							//System.out.println("Server salje poruku za " + text.split("_")[0] + " tekst: " + text.split("_")[1] + " od " + text.split("_")[2]);
							out.println(text);
						} else {
							out.println("");
						}
					}
				}  else {
					out.println("");
				}
				
				sleep(2000);
			} catch (Exception e) {
				Logger.getLogger(MainChatServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
	}
}
