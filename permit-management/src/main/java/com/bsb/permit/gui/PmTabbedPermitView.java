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

	private List<String> permitTabs = null;

	private PmTabbedPermitView() {
		super();

		permitTabs = new ArrayList<String>();
		permitTabs.add(Constants.PM_PERMIT_TYPE_MASTER);
		permitTabs.add(Constants.PM_PERMIT_TYPE_RESERVE);
		permitTabs.add(Constants.PM_PERMIT_TYPE_BACKUP);
		for (String tab : permitTabs) {
			this.addTab(tab, PmPermitView.getInstance(tab));
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
