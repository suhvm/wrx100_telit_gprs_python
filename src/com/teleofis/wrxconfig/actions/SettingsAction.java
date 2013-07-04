package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;

import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.SettingsDialog;

public class SettingsAction extends Action {
	
	ApplicationWindow window;
	
	public SettingsAction(ApplicationWindow w) {
		window = w;
		setText("Настройки");
		setToolTipText("Настройки программы");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/gear.png"));
	}

	public void run() {
		SettingsDialog dialog = new SettingsDialog(window.getShell());
		int code = dialog.open();
		if (code == Window.OK) {
			Model.INSTANCE.getModel().setSerialParameters(dialog.getSerialParameters());
			Model.INSTANCE.notifyListeners(this, "configuration");
		}
	}
}
