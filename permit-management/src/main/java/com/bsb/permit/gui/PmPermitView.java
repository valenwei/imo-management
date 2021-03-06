package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

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

		tableModel = new PmTableModel(new String[] { "Permit Id", "Expire Date", "Raw Data", "Import Date", "Status" },
				0);
		tableView = new JTable(tableModel);

		tableView.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		tableView.getColumn("Import Date").setCellRenderer(cellRender);
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

	public Permit getSelectedPermit() {
		if (this.tableView.getSelectedRow() < 0) {
			return null;
		}
		int currentRow = this.tableView.getSelectedRow();
		return new Permit(this.tableView.getValueAt(currentRow, 0).toString(),
				this.tableView.getValueAt(currentRow, 1).toString(),
				this.tableView.getValueAt(currentRow, 2).toString(),
				this.tableView.getValueAt(currentRow, 3).toString(), this.getPermitType(),
				PmShipView.getInstance().getSelectedShip().getImo());
	}

	public void deleteSelectedPermit() {
		if (this.tableView.getSelectedRow() < 0) {
			return;
		}
		int row = this.tableView.getSelectedRow();
		this.tableModel.removeRow(row);
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
						Object[] row = new Object[5];
						row[0] = p.getPermitId();
						row[1] = p.getExpireDate();
						row[2] = p.getRawText();
						row[3] = p.getImportDate();
						row[4] = p.getExpirationStatus();
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
//				PmPermitView.this.fitTableColumns();
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

	public void fitTableColumns() {
		JTableHeader header = this.tableView.getTableHeader();
		int rowCount = this.tableView.getRowCount();

		Enumeration<TableColumn> columns = this.tableView.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) this.tableView.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(this.tableView, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) this.tableView
						.getCellRenderer(row, col).getTableCellRendererComponent(this.tableView,
								this.tableView.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // important
			column.setWidth(width + this.tableView.getIntercellSpacing().width);
		}
	}
}
