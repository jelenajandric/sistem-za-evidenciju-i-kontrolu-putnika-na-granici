package org.unibl.etf.model;

import java.io.Serializable;

public class AddingFileModel implements Serializable {
	private static final long serialVersionUID = 1L;
	String entranceOrExitId;
	int jmb;
	String filename;
	int[] fileData;
	
	public AddingFileModel() {
		super();
	}
	public AddingFileModel(String entranceOrExitId, int jmb, String filename, int[] fileData) {
		super();
		this.entranceOrExitId = entranceOrExitId;
		this.jmb = jmb;
		this.filename = filename;
		this.fileData = fileData;
	}
	public String getEntranceOrExitId() {
		return entranceOrExitId;
	}
	public void setEntranceOrExitId(String entranceOrExitId) {
		this.entranceOrExitId = entranceOrExitId;
	}
	public int getJmb() {
		return jmb;
	}
	public void setJmb(int jmb) {
		this.jmb = jmb;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int[] getFileData() {
		return fileData;
	}
	public void setFileData(int[] fileData) {
		this.fileData = fileData;
	}
	

}
