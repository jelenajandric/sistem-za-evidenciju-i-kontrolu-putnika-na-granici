package org.unibl.etf.model;

public class InitialFormResponse {
	
	private String message;
	private boolean indicator;
	public InitialFormResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InitialFormResponse(String message, boolean indicator) {
		super();
		this.message = message;
		this.indicator = indicator;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isIndicator() {
		return indicator;
	}
	public void setIndicator(boolean indicator) {
		this.indicator = indicator;
	}
	
	

}
