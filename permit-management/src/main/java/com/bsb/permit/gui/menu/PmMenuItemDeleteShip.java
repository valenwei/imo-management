package com.bsb.permit.gui.menu;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.gui.PmConfirmationDialog;
import com.bsb.permit.gui.PmShipView;
import com.bsb.permit.model.Ship;

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
		Ship ship = PmShipView.getInstance().getSelectedShip();
		if (ship != null && PmConfirmationDialog
				.show("Are you sure to delete the ship? Delete this ship will also delete the related permits.")
				.isConfirmed()) {

			DataAccessor accessor = new DataAccessor();
			try {
				if (accessor.deleteShip(ship)) {
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					accessor.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
