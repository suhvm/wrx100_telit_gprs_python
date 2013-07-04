package com.teleofis.wrxconfig;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import com.teleofis.wrxconfig.model.Parameter;

public class EditDialog extends Dialog {
	
	private final Parameter parameter;
	
	
	private Label lblNewLabel;
	private Label label;
	private Label label_1;
	private Text textDescription;
	private Text textName;
	private Text textValue;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public EditDialog(Shell parentShell, Parameter parameter) {
		super(parentShell);
		if(parameter == null) {
			this.parameter = new Parameter();
		} else {
			this.parameter = parameter;
		}
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
		label.setText("Имя переменной:");
		
		textName = new Text(container, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label_1 = new Label(container, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Значение:");
		
		textValue = new Text(container, SWT.BORDER);
		textValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Описание:");
		
		textDescription = new Text(container, SWT.BORDER);
		textDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		fillContent();
		textValue.setFocus();
		textValue.selectAll();

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
		return new Point(450, 183);
	}
	
	protected void okPressed() {
		String name = textName.getText();
		String description = textDescription.getText();
		String value = textValue.getText();
		
		if (name.length() < 2) {
			MessageDialog.openError(getShell(), "Ошибка ввода", "Имя переменной должно быть длиннее 2 символов.");
			return;
		}
		
		parameter.setName(name);
		parameter.setDescription(description);
		parameter.setValue(value);

		super.okPressed();
	}
	
	private void fillContent() {
		if(parameter != null) {
			textDescription.setText(parameter.getDescription());
			textName.setText(parameter.getName());
			textValue.setText(parameter.getValue());
		}
	}
	
	public Parameter getParameter() {
		return parameter;
	}

}
