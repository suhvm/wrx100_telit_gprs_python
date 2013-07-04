package com.teleofis.wrxconfig;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.GridData;

import com.teleofis.wrxconfig.actions.AddAction;
import com.teleofis.wrxconfig.actions.ConnectAction;
import com.teleofis.wrxconfig.actions.DeleteAction;
import com.teleofis.wrxconfig.actions.DisconnectAction;
import com.teleofis.wrxconfig.actions.EditAction;
import com.teleofis.wrxconfig.actions.ExitAction;
import com.teleofis.wrxconfig.actions.FastUploadAction;
import com.teleofis.wrxconfig.actions.MoveDownAction;
import com.teleofis.wrxconfig.actions.MoveUpAction;
import com.teleofis.wrxconfig.actions.OpenAction;
import com.teleofis.wrxconfig.actions.SaveAction;
import com.teleofis.wrxconfig.actions.SaveAsAction;
import com.teleofis.wrxconfig.actions.SettingsAction;
import com.teleofis.wrxconfig.actions.UploadAction;
import com.teleofis.wrxconfig.controller.Controller;
import com.teleofis.wrxconfig.model.Configuration;
import com.teleofis.wrxconfig.model.Parameter;

public class MainWindow extends ApplicationWindow implements PropertyChangeListener {
	
	private Table tableConfiguration;
	
	private TableViewer tableViewer;
	
	/**
	 * Actions
	 */
	SaveAction saveAction;
	SaveAsAction saveAsAction;
	OpenAction openAction;
	ExitAction exitAction;
	SettingsAction settingsAction;
	
	AddAction addAction;
	DeleteAction deleteAction;
	EditAction editAction;
	MoveUpAction moveUpAction;
	MoveDownAction moveDownAction;
	
	ConnectAction connectAction;
	DisconnectAction disconnectAction;
	
	UploadAction uploadAction;
	FastUploadAction fastUploadAction;
	

	/**
	 * Create the application window,
	 */
	public MainWindow() {
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
	}
	
	public TableViewer getViewer() {
		return tableViewer;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
			tableConfiguration = tableViewer.getTable();
			tableConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			
			tableViewer.setLabelProvider(new ConfigurationLabelProvider());
			tableViewer.setContentProvider(new ConfigurationContentProvider());
			
			TableColumn column = new TableColumn(tableConfiguration, SWT.NONE);
			column.setWidth(400);
			column.setText("Описание");
			
			column = new TableColumn(tableConfiguration, SWT.NONE);
			column.setWidth(200);
			column.setText("Имя переменной");
			
			column = new TableColumn(tableConfiguration, SWT.NONE);
			column.setWidth(200);
			column.setText("Значение переменной");
			
			Configuration model = Model.INSTANCE.getModel();
			
			tableViewer.setInput(model);
			tableConfiguration.setLinesVisible(true);
			tableConfiguration.setHeaderVisible(true);
		}
		
		boolean settings = new File("scripts/settings.ini").isFile();
		if(settings) {
			Controller.INSTANCE.openSettings("scripts/settings.ini");
			Model.INSTANCE.getModel().setSettingsPath("scripts/settings.ini");
		}
		
		createEventListeners();

		Model.INSTANCE.addPropertyChangeListener("configuration", this);
		Model.INSTANCE.notifyListeners(this, "configuration");
		Model.INSTANCE.notifyListeners(this, "connection");

		return container;
	}
	
	private void createEventListeners() {
		
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object selectedObject = selection.getFirstElement();
				if(selectedObject instanceof Parameter) {
					Parameter parameter = (Parameter) selectedObject; // this can return null
					if(parameter != null) {
						EditDialog dialog = new EditDialog(getShell(), parameter);
						int code = dialog.open();
						if (code == Window.OK) {
							Model.INSTANCE.notifyListeners(this, "configuration");
						}
					}
				}
			}
		});
		
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		saveAction = new SaveAction(this);
		saveAsAction = new SaveAsAction(this);
		openAction = new OpenAction(this);
		exitAction = new ExitAction(this);
		settingsAction = new SettingsAction(this);
		
		addAction = new AddAction(this);
		deleteAction = new DeleteAction(this);
		editAction = new EditAction(this);
		moveUpAction = new MoveUpAction(this);
		moveDownAction = new MoveDownAction(this);
		
		connectAction = new ConnectAction(this);
		disconnectAction = new DisconnectAction();
		
		uploadAction = new UploadAction(this);
		fastUploadAction = new FastUploadAction(this);
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		
		MenuManager fileMenu = new MenuManager("Файл");
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(openAction);
		fileMenu.add(settingsAction);
		fileMenu.add(exitAction);
		
		MenuManager editMenu = new MenuManager("Редактировать");
		editMenu.add(addAction);
		editMenu.add(deleteAction);
		editMenu.add(editAction);
		editMenu.add(moveUpAction);
		editMenu.add(moveDownAction);
		
		MenuManager commandMenu = new MenuManager("Команда");
		commandMenu.add(connectAction);
		commandMenu.add(disconnectAction);
		commandMenu.add(uploadAction);
		commandMenu.add(fastUploadAction);
		
		menuManager.add(fileMenu);
		menuManager.add(editMenu);
		menuManager.add(commandMenu);

		return menuManager;
	}

	/**
	 * Create the coolbar manager.
	 * @return the coolbar manager
	 */
	@Override
	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);
		
		ToolBarManager toolBar = new ToolBarManager(SWT.NO_FOCUS);
		
		toolBar.add(saveAction);
		toolBar.add(saveAsAction);
		toolBar.add(openAction);
		toolBar.add(settingsAction);
		toolBar.add(new Separator());
		
		toolBar.add(connectAction);
		toolBar.add(disconnectAction);
		toolBar.add(new Separator());
		
		toolBar.add(addAction);
		toolBar.add(deleteAction);
		toolBar.add(editAction);
		toolBar.add(moveUpAction);
		toolBar.add(moveDownAction);
		toolBar.add(new Separator());
		
		toolBar.add(uploadAction);
		toolBar.add(fastUploadAction);
		
		coolBarManager.add(toolBar);
		
		
		return coolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainWindow window = new MainWindow();
			window.setBlockOnOpen(true);
			window.open();
			Controller.INSTANCE.terminate();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("WRX100 Configuration Tool");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("configuration")) {
			tableViewer.setInput(Model.INSTANCE.getModel());
			tableViewer.refresh();
		}
		if(evt.getPropertyName().equals("connection")) {
			boolean connected = Model.INSTANCE.getModel().isConnected();
			if(connected) {
				connectAction.setEnabled(false);
				disconnectAction.setEnabled(true);
				uploadAction.setEnabled(true);
				fastUploadAction.setEnabled(true);
			} else {
				connectAction.setEnabled(true);
				disconnectAction.setEnabled(false);
				uploadAction.setEnabled(false);
				fastUploadAction.setEnabled(false);
			}
		}
	}
}
