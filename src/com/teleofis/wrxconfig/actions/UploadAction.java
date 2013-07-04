package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.MessageBox;

import com.teleofis.wrxconfig.UploadDialog;

public class UploadAction extends Action {
	
	ApplicationWindow window;
	
	public UploadAction(ApplicationWindow w) {
		window = w;
		setText("Загрузить");
		setToolTipText("Загрузить и запустить скрипт");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/drive-upload.png"));
	}
	
	public void run() {
		UploadDialog upload = new UploadDialog(window.getShell(), 1);
		upload.open();
	}
}
