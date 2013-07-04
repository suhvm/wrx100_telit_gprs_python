package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;

import com.teleofis.wrxconfig.MainWindow;
import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.model.Parameter;

public class DeleteAction extends Action {
	ApplicationWindow window;
	
	public DeleteAction(ApplicationWindow w) {
		window = w;
		setText("Удалить");
		setToolTipText("Удалить параметр");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/cross.png"));
	}
	
	public void run() {
		if(window instanceof MainWindow) {
			MainWindow mw = (MainWindow) window;
			IStructuredSelection selection = (IStructuredSelection) mw.getViewer().getSelection();
			for(Object selectedObject : selection.toList()) {
				if(selectedObject instanceof Parameter) {
					Parameter parameter = (Parameter) selectedObject; // this can return null
					if(parameter != null) {
						Model.INSTANCE.getModel().deleteParameter(parameter);
					}
				}
			}
			Model.INSTANCE.notifyListeners(this, "configuration");
		}

	}
}
