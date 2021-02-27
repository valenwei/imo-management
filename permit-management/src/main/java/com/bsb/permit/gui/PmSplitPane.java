package com.bsb.permit.gui;

import javax.swing.JSplitPane;

public class PmSplitPane extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PmSplitPane() {
		super(JSplitPane.HORIZONTAL_SPLIT, PmShipView.getInstance(), PmTabbedPermitView.getInstance());
		this.setDividerLocation(400);
		this.setDividerSize(4);
	}
}
