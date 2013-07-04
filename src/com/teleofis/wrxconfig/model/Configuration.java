package com.teleofis.wrxconfig.model;

import java.util.ArrayList;

import com.teleofis.wrxconfig.transport.SerialParameters;

public class Configuration {
	private ArrayList<Parameter> parameters;
	
	private boolean connected;
	
	private SerialParameters serialParameters;
	
	private String settingsPath;

	/**
	 * Constructor
	 */
	public Configuration() {
		parameters = new ArrayList<Parameter>();
		connected = false;
		settingsPath = "";
	}
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}
	
	public void deleteParameter(Parameter parameter) {
		parameters.remove(parameter);
	}
	
	public Object[] getParametersAsArray() {
		return parameters.toArray();
	}
	
	public ArrayList<Parameter> getParameters() {
		return parameters;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public SerialParameters getSerialParameters() {
		return serialParameters;
	}

	public void setSerialParameters(SerialParameters serialParameters) {
		this.serialParameters = serialParameters;
	}
	
	public void clearParameters() {
		parameters.clear();
	}
	
	public String getSettingsPath() {
		return settingsPath;
	}

	public void setSettingsPath(String settingsPath) {
		this.settingsPath = settingsPath;
	}
}
