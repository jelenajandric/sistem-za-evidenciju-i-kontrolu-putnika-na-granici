package org.unibl.etf.model;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private int jmbg;
	private String name;
	private String surname;
	private boolean isItOnTheWantedList;
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(int jmbg, String name, String suranme, boolean isItOnTheWantedList ) {
		super();
		this.jmbg = jmbg;
		this.name = name;
		this.surname = suranme;
		this.isItOnTheWantedList = isItOnTheWantedList;
	}

	public int getJmbg() {
		return jmbg;
	}

	public void setJmbg(int jmbg) {
		this.jmbg = jmbg;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isItOnTheWantedList() {
		return isItOnTheWantedList;
	}

	public void setItOnTheWantedList(boolean isItOnTheWantedList) {
		this.isItOnTheWantedList = isItOnTheWantedList;
	}

	@Override
	public String toString() {
		return jmbg + "#" + name + "#" + surname + "#" + isItOnTheWantedList;
	}
}
