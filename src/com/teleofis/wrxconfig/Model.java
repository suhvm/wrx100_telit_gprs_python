package com.teleofis.wrxconfig;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.teleofis.wrxconfig.model.Configuration;

/*
 * Singleton class for application model
 */
public enum Model {
	
	INSTANCE;
	
	private Configuration configuration = new Configuration();
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	// Used to update all views which are listening to the global model
	public void notifyListeners(Object source, String propertyName) {
		PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, null, null);
		for (PropertyChangeListener listener : propertyChangeSupport.getPropertyChangeListeners()) {
			listener.propertyChange(event);
		}
	}
	
	public Configuration getModel() {
		return configuration;
	}
	
	public void setModel(Configuration configuration) {
		this.configuration = configuration;
	}
}
