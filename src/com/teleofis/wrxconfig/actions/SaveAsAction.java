package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.controller.Controller;

public class SaveAsAction extends Action {
	ApplicationWindow window;

	public SaveAsAction(ApplicationWindow w) {
		window = w;
		setText("Сохранить как");
		setToolTipText("Сохранить конфигурацию как");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/disk-pencil.png"));
	}

	public void run() {
		FileDialog dialog = new FileDialog(window.getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.ini", "*.*" });
		dialog.setFilterNames(new String[] { "*.ini File", "All Files" });
		
		String fileSelected = dialog.open();
		
		if (fileSelected != null) {
			Controller.INSTANCE.dumpSettings(fileSelected);
			Model.INSTANCE.getModel().setSettingsPath(fileSelected);
		}
	}
}
