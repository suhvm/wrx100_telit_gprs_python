package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;

import com.teleofis.wrxconfig.EditDialog;
import com.teleofis.wrxconfig.MainWindow;
import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.model.Parameter;

public class EditAction extends Action {
	ApplicationWindow window;
	
	public EditAction(ApplicationWindow w) {
		window = w;
		setText("Редактировать");
		setToolTipText("Редактировать параметр");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/document-pencil.png"));
	}
	
	public void run() {
		if(window instanceof MainWindow) {
			MainWindow mw = (MainWindow) window;
			IStructuredSelection selection = (IStructuredSelection) mw.getViewer().getSelection();
			Object selectedObject = selection.getFirstElement();
			if(selectedObject instanceof Parameter) {
				Parameter parameter = (Parameter) selectedObject;
				if(parameter != null) {
					EditDialog dialog = new EditDialog(window.getShell(), parameter);
					int code = dialog.open();
					if (code == Window.OK) {
						Model.INSTANCE.notifyListeners(this, "configuration");
					}
				}
			}
		}

	}
}
