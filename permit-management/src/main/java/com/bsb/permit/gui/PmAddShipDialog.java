package com.bsb.permit.gui;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.model.Ship;

public final class PmAddShipDialog {

	private class AddShipRunnable implements Runnable {

		private Ship ship;

		public AddShipRunnable(Ship ship) {
			this.ship = ship;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// insert into database
			DataAccessor accessor = new DataAccessor();
			if (accessor.addShip(ship) > 0) {
				PmShipView.getInstance().addShip(ship);
			}

			try {
				accessor.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// add to GUI
		}

	}

	private PmAddShipDialog() {

	}

	public static void show() {
		final JTextField imo = new JTextField();
		final JTextField shipName = new JTextField();
		final JTextField shipDescription = new JTextField();
		final JTextField ownerCompany = new JTextField();

		final Object[] message = { "IMO", imo, "Ship Name", shipName, "Description", shipDescription, "Owner Company",
				ownerCompany };

		final int option = JOptionPane.showConfirmDialog(null, message, "Add Ship", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (shipName.getText().length() > 0 && option == JOptionPane.OK_OPTION) {
			new PmAddShipDialog().addShip(new Ship(shipName.getText(), shipDescription.getText(), imo.getText(),
					ownerCompany.getText(), "", "", "", ""));
		}
	}

	private void addShip(Ship ship) {
		Thread t = new Thread(new AddShipRunnable(ship));
		t.start();
	}
}
