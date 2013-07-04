package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.teleofis.wrxconfig.controller.Controller;

public class SaveAction extends Action {
	ApplicationWindow window;

	public SaveAction(ApplicationWindow w) {
		window = w;
		setText("Сохранить");
		setToolTipText("Сохранить конфигурацию");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/disk-black.png"));
	}

	public void run() {
		Controller.INSTANCE.dumpSettings(null);
	}
}
