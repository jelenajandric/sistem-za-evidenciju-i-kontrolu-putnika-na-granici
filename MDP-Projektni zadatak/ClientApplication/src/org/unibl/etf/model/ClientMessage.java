package org.unibl.etf.model;

public class ClientMessage {
	private String sender;
	private String content;
	private String dateAndTime;
	
	public ClientMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClientMessage(String sender, String content, String dateAndTime) {
		super();
		this.sender = sender;
		this.content = content;
		this.dateAndTime = dateAndTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	
}
