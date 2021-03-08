package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bsb.permit.util.Constants;

public class PmPermitViewCellRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7905580763004874520L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String expirationStatus = table.getValueAt(row, 4).toString();
		if (expirationStatus.equalsIgnoreCase(Constants.PM_PERMIT_EXPIRATION_STATUS_EXPIRED)) {
			cell.setBackground(Color.GRAY);
		} else if (expirationStatus.equalsIgnoreCase(Constants.PM_PERMIT_EXPIRATION_STATUS_EXPIRING)) {
			cell.setBackground(Color.YELLOW);
		} else {
			cell.setBackground(Color.WHITE);
		}
		return cell;
	}
}
