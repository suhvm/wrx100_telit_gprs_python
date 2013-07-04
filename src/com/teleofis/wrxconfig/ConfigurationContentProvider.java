package com.teleofis.wrxconfig;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.teleofis.wrxconfig.model.Configuration;

public class ConfigurationContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		Object[] params = ((Configuration) inputElement).getParametersAsArray();
		return params;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
}
