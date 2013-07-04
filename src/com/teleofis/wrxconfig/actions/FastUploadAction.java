package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.teleofis.wrxconfig.UploadDialog;

public class FastUploadAction extends Action {
	
	ApplicationWindow window;
	
	public FastUploadAction(ApplicationWindow w) {
		window = w;
		setText("Загрузить конфигурацию");
		setToolTipText("Загрузить конфигурационный файл");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/rocket-fly.png"));
	}
	
	public void run() {
		UploadDialog upload = new UploadDialog(window.getShell(), 0);
		upload.open();
	}

}
