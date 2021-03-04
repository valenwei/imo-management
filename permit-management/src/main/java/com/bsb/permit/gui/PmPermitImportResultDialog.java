package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bsb.permit.model.Permit;

public class PmPermitImportResultDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353449955389795947L;
	private static final String DIALOG_TITLE = "Import Result";
	private JTable tableView = null;
	private DefaultTableModel tableModel = null;

	public PmPermitImportResultDialog(JFrame parent) {
		super(parent, DIALOG_TITLE, true);
//		Dimension size = parent.getSize();
		setBounds(parent.getX() + 100, parent.getY() + 100, 800, 350);
		init();
	}

	private void init() {
		this.setLayout(null);

		Container container = this.getContentPane();
		JScrollPane pane = new JScrollPane();

		tableModel = new DefaultTableModel(new String[] { "Permit Id", "Expire Date", "Raw Data", "Status" }, 0);
		tableView = new JTable(tableModel);

		tableView.setForeground(Color.BLACK);
		tableView.setFont(new Font(null, Font.PLAIN, 14));
		tableView.setRowSelectionAllowed(true);
		tableView.setSelectionForeground(Color.DARK_GRAY);
		tableView.setSelectionBackground(Color.LIGHT_GRAY);
		tableView.setGridColor(Color.GRAY);

		tableView.getTableHeader().setFont(new Font(null, Font.BOLD, 14));
		tableView.getTableHeader().setForeground(Color.BLACK);
		tableView.getTableHeader().setReorderingAllowed(false);
		tableView.setRowHeight(30);
		tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		pane.setViewportView(this.tableView);

		container.add(pane);
		pane.setBounds(4, 4, 776, 304);

		container.add(new JLabel("OK"));
	}

	public void addPermit(Permit permit, int result) {
		Object[] row = new Object[4];
		row[0] = "1";
		row[1] = "2";
		row[2] = "3";
		row[3] = "4";
		this.tableModel.addRow(row);

	}
}
