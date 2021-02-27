package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.model.Permit;
import com.bsb.permit.model.Ship;
import com.bsb.permit.util.Awaitility;

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

		tableModel = new DefaultTableModel(new String[] { "IMO", "Ship Name", "Description", "Owner Company" }, 0);
		tableView = new JTable(tableModel);
		tableView.getSelectionModel().addListSelectionListener(this);

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
		try {
			while (true) {
				// Tye load ships
				try {
					DataAccessor accessor = new DataAccessor();
					List<Ship> ships = accessor.getShips();
					if (ships.size() > 0) {
						for (Ship ship : ships) {
							addShip(ship);
						}
						break;
					} else {
						logger.info("Load ships failed or no ship loaded. Tra again after 1 minute.");
						Awaitility.await(60 * 1000);
					}
					accessor.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addShip(Ship ship) {

		if (null == ship) {
			return;
		}

		Object[] row = new Object[4];
		row[0] = ship.getImo();
		row[1] = ship.getShipName();
		row[2] = ship.getDescription();
		row[3] = ship.getOwnerCompany();

		this.tableModel.addRow(row);
	}

	public String getSelectedImo() {
		int row = this.tableView.getSelectedRow();
		if (row > -1) {
			return this.tableModel.getValueAt(row, 0).toString();
		}
		return "";
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (this.lastSelectRow == this.tableView.getSelectedRow()) {
			return;
		}
		this.lastSelectRow = this.tableView.getSelectedRow();
		showPermits();
	}

	private void showPermits() {
		PmPermitView currentPermitView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
		while (currentPermitView.getTableModel().getRowCount() > 0) {
			currentPermitView.getTableModel().removeRow(0);
		}
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DataAccessor accessor = new DataAccessor();
				try {
					PmPermitView currentView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
					List<Permit> permits = accessor.getPermits(currentView.getPermitType(),
							PmShipView.getInstance().getSelectedImo());
					for (Permit p : permits) {
						Object[] row = new Object[4];
						row[0] = p.getPermitId();
						row[1] = p.getExpireDate();
						row[2] = p.getRawText();
						row[3] = p.getStatus();
						currentView.getTableModel().addRow(row);
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

		});
		t.start();
	}
}
