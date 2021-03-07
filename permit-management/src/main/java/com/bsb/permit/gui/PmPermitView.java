package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.model.Permit;
import com.bsb.permit.model.PermitType;
import com.bsb.permit.model.Ship;
import com.bsb.permit.model.StandardPermitTypes;

public class PmPermitView extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3004569685363695355L;

	private PermitType permitType;
	private JTable tableView = null;
	private DefaultTableModel tableModel = null;
	private Ship currentShip = null;
	private final Semaphore semaphore = new Semaphore(1);

	private final static Map<String, PmPermitView> instances = new HashMap<String, PmPermitView>();

	public static synchronized PmPermitView getInstance(String viewType) {
		if (false == instances.containsKey(viewType)) {
			instances.put(viewType, new PmPermitView(StandardPermitTypes.mapPermitType(viewType)));
		}
		return instances.get(viewType);
	}

	private PmPermitView(PermitType viewType) {
		super();
		this.permitType = viewType;

		tableModel = new DefaultTableModel(new String[] { "Permit Id", "Expire Date", "Raw Data", "Status" }, 0);
		tableView = new JTable(tableModel);

		tableView.setForeground(Color.BLACK);
		tableView.setFont(new Font(null, Font.PLAIN, 14));
		tableView.setRowSelectionAllowed(true);
		tableView.setSelectionForeground(Color.BLUE);
		tableView.setSelectionBackground(Color.LIGHT_GRAY);
		tableView.setGridColor(Color.GRAY);

		tableView.getTableHeader().setFont(new Font(null, Font.BOLD, 14));
		tableView.getTableHeader().setForeground(Color.BLACK);
		tableView.getTableHeader().setReorderingAllowed(false);
		tableView.setRowHeight(30);
		tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		PmPermitViewCellRender cellRender = new PmPermitViewCellRender();
		tableView.getColumn("Permit Id").setCellRenderer(cellRender);
		tableView.getColumn("Expire Date").setCellRenderer(cellRender);
		tableView.getColumn("Raw Data").setCellRenderer(cellRender);
		tableView.getColumn("Status").setCellRenderer(cellRender);

		this.setViewportView(this.tableView);
	}

	public DefaultTableModel getTableModel() {
		return this.tableModel;
	}

	public List<Integer> getSelectedRows() {
		List<Integer> rows = new ArrayList<Integer>();
		for (int i : this.tableView.getSelectedRows()) {
			rows.add(Integer.valueOf(i));
		}
		return rows;
	}

	public PermitType getPermitType() {
		return permitType;
	}

	public synchronized void showPermitsOfShip(Ship ship, boolean forceRefresh) {
		if (null == ship) {
			return;
		}

		if (forceRefresh) {
			this.currentShip = null;
		}

		if (null != this.currentShip && this.currentShip.getImo().equalsIgnoreCase(ship.getImo())) {
			return;
		}

		this.currentShip = ship;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					PmPermitView.this.semaphore.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
				while (PmPermitView.this.tableModel.getRowCount() > 0) {
					PmPermitView.this.tableModel.removeRow(0);
				}

				DataAccessor accessor = new DataAccessor();
				try {
					List<Permit> permits = accessor.getPermits(PmPermitView.this.getPermitType(),
							PmPermitView.this.currentShip.getImo());
					for (Permit p : permits) {
						Object[] row = new Object[4];
						row[0] = p.getPermitId();
						row[1] = p.getExpireDate();
						row[2] = p.getRawText();
						row[3] = p.getStatus();
						tableModel.addRow(row);
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

				PmPermitView.this.semaphore.release();
			}

		});
		t.start();
	}

	public void clearView() {
		this.currentShip = null;
		while (this.tableModel.getRowCount() > 0) {
			this.tableModel.removeRow(0);
		}
	}
}
