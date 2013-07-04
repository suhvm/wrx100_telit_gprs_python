package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.controller.Controller;

public class ConnectAction extends Action {
	
	ApplicationWindow window;

	public ConnectAction(ApplicationWindow w) {
		window = w;
		setText("Открыть порт");
		setToolTipText("Открыть последовательный порт");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/network-status.png"));
	}
	
	public void run() {
		if(Controller.INSTANCE.open()) {
			Model.INSTANCE.getModel().setConnected(true);
			Model.INSTANCE.notifyListeners(this, "connection");
		} else {
			MessageBox messageBox = new MessageBox(window.getShell(), SWT.ICON_ERROR);
	        messageBox.setText("Ошибка");
	        messageBox.setMessage("Ошибка открытия порта. Проверьте параметры.");
	        messageBox.open();
		}
	}
}
