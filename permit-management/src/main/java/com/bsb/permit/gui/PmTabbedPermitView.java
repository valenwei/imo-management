package com.bsb.permit.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.model.Ship;
import com.bsb.permit.util.Constants;

public class PmTabbedPermitView extends JTabbedPane implements ChangeListener {

	/**
	 * 
	 */

	private static Logger logger = LoggerFactory.getLogger(PmTabbedPermitView.class);

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
		logger.debug(this.getTitleAt(selected) + " is selected");
		PmPermitView.getInstance(this.getTitleAt(selected))
				.showPermitsOfShip(PmShipView.getInstance().getSelectedShip(), false);

		refreshImportMenu(this.getTitleAt(selected));
	}

	public void refreshTabs(Ship ship) {
		if (null == ship) {
			return;
		}

		for (String permitType : permitTypes) {
			PmPermitView.getInstance(permitType).clearView();
		}

		while (this.getTabCount() > 1) {
			this.removeTabAt(this.getTabCount() - 1);
		}

		if (ship.hasBackup()) {
			this.insertTab(Constants.PM_PERMIT_TYPE_BACKUP, null,
					PmPermitView.getInstance(Constants.PM_PERMIT_TYPE_BACKUP), "", this.getTabCount());
		}

		if (ship.hasReserve1()) {
			this.insertTab(Constants.PM_PERMIT_TYPE_RESERVE1, null,
					PmPermitView.getInstance(Constants.PM_PERMIT_TYPE_RESERVE1), "", this.getTabCount());
		}

		if (ship.hasReserve2()) {
			this.insertTab(Constants.PM_PERMIT_TYPE_RESERVE2, null,
					PmPermitView.getInstance(Constants.PM_PERMIT_TYPE_RESERVE2), "", this.getTabCount());
		}
	}

	private void refreshImportMenu(String text) {
		JMenuBar menuBar = PmMainFrame.getInstance().getJMenuBar();
		JMenu permitMenu = menuBar.getMenu(0);
		JMenu importMenu = (JMenu) permitMenu.getMenuComponent(0);

		int count = importMenu.getMenuComponentCount();
		for (int i = 0; i < count; i++) {
			JMenuItem item = (JMenuItem) importMenu.getMenuComponent(i);
			if (item.getText().equalsIgnoreCase(text)) {
				item.setEnabled(true);
			} else {
				item.setEnabled(false);
			}
		}
	}

}
