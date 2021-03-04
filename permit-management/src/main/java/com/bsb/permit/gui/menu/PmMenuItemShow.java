package com.bsb.permit.gui.menu;

import com.bsb.permit.gui.PmSplitPane;

public class PmMenuItemShow extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514315569158975038L;

	public PmMenuItemShow() {
		super();
	}

	public PmMenuItemShow(String text) {
		super(text);
	}

	@Override
	public void performAction() {

		PmSplitPane.getInstance().switchView();
//		// TODO Auto-generated method stub
//		PmPermitView currentPermitView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
//		while (currentPermitView.getTableModel().getRowCount() > 0) {
//			currentPermitView.getTableModel().removeRow(0);
//		}
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

}
