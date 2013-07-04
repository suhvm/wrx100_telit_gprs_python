package com.teleofis.wrxconfig;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.teleofis.wrxconfig.model.Parameter;

public class ConfigurationLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}

	@Override
	public String getColumnText(Object arg0, int arg1) {
		String label = "";
		switch(arg1) {
		case 0:
			label = ((Parameter) arg0).getDescription();
			break;
		case 1:
			label = ((Parameter) arg0).getName();
			break;
		case 2:
			label = ((Parameter) arg0).getValue();
			break;
		}
		return label;
	}

}
