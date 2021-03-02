package com.bsb.permit.gui.menu;

import com.bsb.permit.gui.PmMainFrame;
import com.bsb.permit.gui.PmShipDialogAdd;

public class PmMenuItemAddShip extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2539191790630040062L;

	public PmMenuItemAddShip() {
		super();
	}

	public PmMenuItemAddShip(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		new PmShipDialogAdd(PmMainFrame.getInstance()).setVisible(true);
	}

}
