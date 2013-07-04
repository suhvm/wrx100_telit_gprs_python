package com.teleofis.wrxconfig.model;

public class Parameter {
	
	private String name = "";
	
	private String description = "";
	
	private String value = "";
	
	public Parameter() {
	}
	
	public Parameter(String name) {
		this.name = name;
	}
	
	public Parameter(String name, String description, String value) {
		this.name = name;
		this.description = description;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name + "::" + value + "::" + description;
	}
}
