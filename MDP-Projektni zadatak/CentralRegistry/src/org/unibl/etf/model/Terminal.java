package org.unibl.etf.model;

import java.io.Serializable;

public class Terminal implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int numOfEnterances;
	private int numOfExits;
	private String name;
	
	
	public Terminal() {
		super();
	}

	public Terminal(int id, int numOfEnterances, int numOfExits, String name) {
		super();
		this.id = id;
		this.numOfEnterances = numOfEnterances;
		this.numOfExits = numOfExits;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumOfEnterances() {
		return numOfEnterances;
	}

	public void setNumOfEnterances(int numOfEnterances) {
		this.numOfEnterances = numOfEnterances;
	}

	public int getNumOfExits() {
		return numOfExits;
	}

	public void setNumOfExits(int numOfExits) {
		this.numOfExits = numOfExits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
