package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.exception.PmException;
import com.bsb.permit.model.Ship;
import com.bsb.permit.util.Awaitility;
import com.bsb.permit.util.Constants;

public class PmShipView extends JScrollPane implements Runnable, ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 993152720756452388L;

	private static Logger logger = LoggerFactory.getLogger(PmShipView.class);

	private static PmShipView instance = null;

	public static synchronized PmShipView getInstance() {
		if (null == instance) {
			instance = new PmShipView();
		}
		return instance;
	}

	private JTable tableView;
	private DefaultTableModel tableModel = null;
	private int lastSelectRow = -1;

	private PmShipView() {
		super();

		tableModel = new PmTableModel(new String[] { "IMO", "Ship Name", "Description", "Owner Company", "Master",
				"Backup", "Reserve1", "Reserve2" }, 0);
		tableView = new JTable(tableModel);
		tableView.getSelectionModel().addListSelectionListener(this);
		tableView.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

		this.setViewportView(this.tableView);
//		this.tree = new JTree();
//		this.tree.addTreeSelectionListener(this);
//		this.setViewportView(this.tree);
//
//		this.rootNode = new DefaultMutableTreeNode();
//		this.rootNode.setUserObject("Ships");
//		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
//		model.setRoot(this.rootNode);
//		model.reload(this.rootNode);

		loadShips();
	}

	private void loadShips() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean shipLoaded = false;
		while (!shipLoaded) {
			// Try load ships
			DataAccessor accessor = null;
			try {
				accessor = new DataAccessor();
				List<Ship> ships = accessor.getShips();
				if (ships.size() > 0) {
					for (Ship ship : ships) {
						addShip(ship);
					}
					logger.info("{} ships loaded.", ships.size());
				} else {
					logger.info("No ships found.");
				}
				shipLoaded = true;
			} catch (PmException e) {
				e.printStackTrace();
				logger.info("Load ships failed. Tra again after 1 minute.");
				Awaitility.await60s();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("Load ships failed. Tra again after 1 minute.");
				Awaitility.await60s();
			} finally {
				if (null != accessor) {
					try {
						accessor.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

//			PmShipView.this.fitTableColumns();
		}
	}

	public void addShip(Ship ship) {

		if (null == ship) {
			return;
		}

		Object[] row = new Object[8];
		row[0] = ship.getImo();
		row[1] = ship.getShipName();
		row[2] = ship.getDescription();
		row[3] = ship.getOwnerCompany();
		row[4] = ship.getMaster();
		row[5] = ship.getBackup();
		row[6] = ship.getReserve1();
		row[7] = ship.getReserve2();

		this.tableModel.addRow(row);
	}

	public String getSelectedImo() {
		int row = this.tableView.getSelectedRow();
		if (row > -1) {
			return this.tableModel.getValueAt(row, 0).toString();
		}
		return "";
	}

	public Ship getSelectedShip() {
		if (this.lastSelectRow < 0) {
			return new Ship(Constants.PM_SHIP_DUMMY, "", "", "", "", "", "", "");
		}
		return new Ship(this.tableView.getValueAt(this.lastSelectRow, 1).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 2).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 0).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 3).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 4).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 5).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 6).toString(),
				this.tableView.getValueAt(this.lastSelectRow, 7).toString());
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (this.lastSelectRow == this.tableView.getSelectedRow()) {
			return;
		}
		this.lastSelectRow = this.tableView.getSelectedRow();

		if (this.lastSelectRow > -1) {
			showPermits(new Ship(this.tableView.getValueAt(this.lastSelectRow, 1).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 2).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 0).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 3).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 4).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 5).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 6).toString(),
					this.tableView.getValueAt(this.lastSelectRow, 7).toString()));
		}
	}

	private void showPermits(Ship ship) {

		PmTabbedPermitView.getInstance().refreshTabs(ship);

		PmPermitView currentPermitView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
		currentPermitView.showPermitsOfShip(ship, true);
//		
//		while (currentPermitView.getTableModel().getRowCount() > 0) {
//			currentPermitView.getTableModel().removeRow(0);
//		}
//		PmTabbedPermitView.getInstance().refreshTabs(ship);
//
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				DataAccessor accessor = new DataAccessor();
//				try {
//					PmPermitView currentView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
//					List<Permit> permits = accessor.getPermits(currentView.getPermitType(),
//							PmShipView.getInstance().getSelectedImo());
//					for (Permit p : permits) {
//						Object[] row = new Object[4];
//						row[0] = p.getPermitId();
//						row[1] = p.getExpireDate();
//						row[2] = p.getRawText();
//						row[3] = p.getStatus();
//						currentView.getTableModel().addRow(row);
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					try {
//						accessor.close();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//
//		});
//		t.start();
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
