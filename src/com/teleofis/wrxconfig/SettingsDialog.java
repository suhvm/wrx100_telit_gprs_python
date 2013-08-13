package com.teleofis.wrxconfig;

import jssc.SerialPort;
import jssc.SerialPortList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.teleofis.wrxconfig.transport.SerialParameters;
import org.eclipse.swt.widgets.Button;

public class SettingsDialog extends Dialog {
	private Label label;
	private Label label_1;
	private Combo comboPort;
	private Combo comboBaudrate;
	
	private SerialParameters serialParameters;
	private Label label_2;
	private Button buttonHwfc;
	
//	private String workDirectory;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SettingsDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Последовательный порт");
		
		comboPort = new Combo(container, SWT.NONE);
		comboPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label_1 = new Label(container, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Скорость порта");
		
		comboBaudrate = new Combo(container, SWT.NONE);
		comboBaudrate.setItems(new String[] {"115200", "57600", "19200", "14400", "9600"});
		comboBaudrate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		try {
			comboBaudrate.select(comboBaudrate.indexOf(Integer.toString(Model.INSTANCE.getModel().getSerialParameters().getBaudrate())));
		} catch (Exception e) {
		}
		
		label_2 = new Label(container, SWT.NONE);
		label_2.setText("Аппаратный контроль потока");
			
		buttonHwfc = new Button(container, SWT.CHECK);
		
		fillComboPort();

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 198);
	}
	
	protected void okPressed() {
		String port = comboPort.getText();
		String baudrate = comboBaudrate.getText();
		
		if(port != null && port != "") {
			serialParameters = new SerialParameters(port, Integer.parseInt(baudrate), 
					SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, 
					buttonHwfc.getSelection());
		}
		
		super.okPressed();
	}
	
	private void fillComboPort() {
		String[] portList = SerialPortList.getPortNames();
		if(portList.length > 0) {
			comboPort.setItems(portList);
			try {
				int idx = comboPort.indexOf(Model.INSTANCE.getModel().getSerialParameters().getName());
				if(idx >= 0) {
					comboPort.select(idx);
				} else {
					comboPort.select(0);
				}
			} catch (Exception e) {
			}
		}
	}
	
	public SerialParameters getSerialParameters() {
		return serialParameters;
	}
}
