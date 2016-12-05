package com.polaris.rest;

import java.io.Serializable;

public class LivySubmitRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String file;
	private String className;
	private String[] args;
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
}
