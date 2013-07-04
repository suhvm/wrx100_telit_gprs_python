package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.controller.Controller;

public class DisconnectAction extends Action {
	
	public DisconnectAction() {
		setText("Закрыть порт");
		setToolTipText("Закрыть последовательный порт");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/network-status-offline.png"));
	}
	
	public void run() {
		if(Controller.INSTANCE.close()) {
			Model.INSTANCE.getModel().setConnected(false);
			Model.INSTANCE.notifyListeners(this, "connection");
		}
	}
}
