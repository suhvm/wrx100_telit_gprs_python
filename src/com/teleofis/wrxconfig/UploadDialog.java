package com.teleofis.wrxconfig;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.GridData;

import com.teleofis.wrxconfig.controller.Controller;

public class UploadDialog extends Dialog implements IProgress {
	
	Button okButton;
	private Label label;
	private ProgressBar progressBar;
	private Label lblNewLabel;
	private final int uploadMode;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public UploadDialog(Shell parentShell, int uploadMode) {
		super(parentShell);
		this.uploadMode = uploadMode;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		
		label = new Label(container, SWT.NONE);
		label.setText("Выполняется загрузка скрипта...");
		
		progressBar = new ProgressBar(container, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblNewLabel = new Label(container, SWT.RIGHT);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Controller.INSTANCE.startUpload(this, uploadMode);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(367, 169);
	}

	@Override
	public void setTotal(final int total) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				progressBar.setMaximum(total);
			}
		});
	}

	@Override
	public void setCompleted(final int completeValue) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				progressBar.setSelection(completeValue);
				if(completeValue == 100) {
					okButton.setEnabled(true);
				}
			}
		});
	}

	@Override
	public void setError(final int errorCode) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if(errorCode == 1) {
					lblNewLabel.setText("Ошибка записи. Проверьте подключение модема.");
				} else {
					lblNewLabel.setText("Успешно.");
				}
			}
		});
	}

}
