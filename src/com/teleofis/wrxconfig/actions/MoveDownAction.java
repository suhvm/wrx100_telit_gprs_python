package com.teleofis.wrxconfig.actions;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;

import com.teleofis.wrxconfig.MainWindow;
import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.model.Parameter;

public class MoveDownAction extends Action {
	ApplicationWindow window;
	
	public MoveDownAction(ApplicationWindow w) {
		window = w;
		setText("Переместить вниз");
		setToolTipText("Переместить параметр вниз");
		setImageDescriptor(ImageDescriptor.createFromFile(null, "icons/arrow-270.png"));
	}
	
	public void run() {
		if(window instanceof MainWindow) {
			MainWindow mw = (MainWindow) window;
			IStructuredSelection selection = (IStructuredSelection) mw.getViewer().getSelection();
			Object selectedObject = selection.getFirstElement();
			if(selectedObject instanceof Parameter) {
				Parameter parameter = (Parameter) selectedObject;
				if(parameter != null) {
					ArrayList<Parameter> parameters = Model.INSTANCE.getModel().getParameters();
					int index = parameters.indexOf(parameter);
					int size = parameters.size();
					if((index >= 0) && (index < (size - 1))) {
						Collections.swap(parameters, index, index + 1);
						Model.INSTANCE.notifyListeners(this, "configuration");
					}
				}
			}
		}

	}
}
