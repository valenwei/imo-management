package com.bsb.permit.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bsb.permit.util.Constants;

public class PmTabbedPermitView extends JTabbedPane implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823416119192512319L;
	private static PmTabbedPermitView instance = null;

	public static synchronized PmTabbedPermitView getInstance() {
		if (null == instance) {
			instance = new PmTabbedPermitView();
		}
		return instance;
	}

	private List<String> permitTypes = null;

	private PmTabbedPermitView() {
		super();
		permitTypes = new ArrayList<String>();
		permitTypes.add(Constants.PM_PERMIT_TYPE_MASTER);
		permitTypes.add(Constants.PM_PERMIT_TYPE_BACKUP);
		permitTypes.add(Constants.PM_PERMIT_TYPE_RESERVE1);
		permitTypes.add(Constants.PM_PERMIT_TYPE_RESERVE2);

		for (String permitType : permitTypes) {
			this.addTab(permitType, PmPermitView.getInstance(permitType));
		}

		this.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int selected = this.getSelectedIndex();
		System.out.println(this.getTitleAt(selected));
	}

}
