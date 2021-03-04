package com.bsb.permit.gui;

import javax.swing.JSplitPane;

public class PmSplitPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static PmSplitPane instance = null;

	public synchronized static PmSplitPane getInstance() {
		if (null == instance) {
			instance = new PmSplitPane();
		}
		return instance;
	}

	private PmSplitPane() {
		super(JSplitPane.VERTICAL_SPLIT, PmShipView.getInstance(), PmTabbedPermitView.getInstance());
		this.setDividerLocation(100);
		this.setDividerSize(4);
	}

	public void switchView() {
		int viewOrientation = this.getOrientation();
		if (viewOrientation == JSplitPane.HORIZONTAL_SPLIT) {
			this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		} else {
			this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		}
	}
}
