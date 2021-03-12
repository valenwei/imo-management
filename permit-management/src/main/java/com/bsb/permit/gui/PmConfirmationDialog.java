package com.bsb.permit.gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public final class PmConfirmationDialog {

	private PmConfirmationDialog() {

	}

	public static PmConfirmation show(String message) {
		final JLabel waring = new JLabel(message);

		final Object[] objs = { "Waring: ", waring };

		final int option = JOptionPane.showConfirmDialog(null, objs, "Confirmation", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		return new PmConfirmation(option == JOptionPane.OK_OPTION, false);
	}
}
