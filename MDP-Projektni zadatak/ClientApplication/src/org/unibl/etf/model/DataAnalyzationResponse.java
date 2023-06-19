package org.unibl.etf.model;

public class DataAnalyzationResponse {
	private boolean indicator;
	private String controlType;
	private int jmb;
	
	public DataAnalyzationResponse() {
		super();
	}
	public DataAnalyzationResponse(boolean indicator, int jmb, String controlType) {
		super();
		this.indicator = indicator;
		this.jmb = jmb;
		this.setControlType(controlType);
	}
	public boolean isIndicator() {
		return indicator;
	}
	public void setIndicator(boolean indicator) {
		this.indicator = indicator;
	}
	public int getJmb() {
		return jmb;
	}
	public void setJmb(int jmb) {
		this.jmb = jmb;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
}
