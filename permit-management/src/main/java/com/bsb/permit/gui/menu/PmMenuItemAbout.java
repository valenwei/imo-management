package com.bsb.permit.gui.menu;

import com.bsb.permit.gui.PmMainFrame;
import com.bsb.permit.gui.PmShipDialogAdd;

public class PmMenuItemAbout extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2539191790630040062L;

	public PmMenuItemAbout() {
		super();
	}

	public PmMenuItemAbout(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		new PmShipDialogAdd(PmMainFrame.getInstance()).setVisible(true);
	}

}
