package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.controller.Controller;

public class OpenAction extends Action {
	ApplicationWindow window;

	public OpenAction(ApplicationWindow w) {
		window = w;
		setText("Открыть");
		setToolTipText("Открыть конфигурацию");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/folder-open.png"));
	}

	public void run() {
		FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.ini", "*.*" });
		dialog.setFilterNames(new String[] { "*.ini File", "All Files" });
		
		String fileSelected = dialog.open();
		if (fileSelected != null) {
			Controller.INSTANCE.openSettings(fileSelected);
			Model.INSTANCE.getModel().setSettingsPath(fileSelected);
			Model.INSTANCE.notifyListeners(this, "configuration");
		}
	}
}
