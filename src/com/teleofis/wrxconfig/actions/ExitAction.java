package com.teleofis.wrxconfig.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;


public class ExitAction extends Action {
	ApplicationWindow window;

	public ExitAction(ApplicationWindow w) {
		window = w;
		setText("Выйти");
		setToolTipText("Выйти из приложения");
	}

	public void run() {
		window.close();
	}
}
