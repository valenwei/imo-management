package com.bsb.permit.gui.menu;

import com.bsb.permit.gui.PmConfirmationDialog;

public class PmMenuItemDeleteShip extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2539191790630040062L;

	public PmMenuItemDeleteShip() {
		super();
	}

	public PmMenuItemDeleteShip(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		if (PmConfirmationDialog
				.show("Are you sure to delete the ship? Delete this ship will also delete the related permits.")
				.isConfirmed()) {

		}
	}

}
