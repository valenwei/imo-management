package com.bsb.permit.gui;

import javax.swing.table.DefaultTableModel;

public class PmTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132859859366658306L;

	public PmTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
