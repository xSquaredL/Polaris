package com.polaris.rest;

import java.io.Serializable;

public class Batch implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String[] log;
	private String state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[] getLog() {
		return log;
	}
	public void setLog(String[] log) {
		this.log = log;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
