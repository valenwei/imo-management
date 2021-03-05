package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PmPermitImportResultCellRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7905580763004874520L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String state = table.getValueAt(row, 3).toString();
		if (state.equalsIgnoreCase("New Added")) {
			cell.setBackground(Color.GREEN);
		}

		if (state.equalsIgnoreCase("Updated")) {
			cell.setBackground(Color.YELLOW);
		}

		if (state.equalsIgnoreCase("")) {
			cell.setBackground(Color.LIGHT_GRAY);
		}

		return cell;
	}
}
