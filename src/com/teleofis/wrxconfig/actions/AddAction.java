package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;

import com.teleofis.wrxconfig.EditDialog;
import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.model.Parameter;

public class AddAction extends Action {
	ApplicationWindow window;
	
	public AddAction(ApplicationWindow w) {
		window = w;
		setText("Добавить");
		setToolTipText("Добавить параметр");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/plus.png"));
	}
	
	public void run() {
		EditDialog dialog = new EditDialog(window.getShell(), null);
		int code = dialog.open();
		if (code == Window.OK) {
			Parameter parameter = dialog.getParameter();
			Model.INSTANCE.getModel().addParameter(parameter);
			Model.INSTANCE.notifyListeners(this, "configuration");
		}
	}
}
