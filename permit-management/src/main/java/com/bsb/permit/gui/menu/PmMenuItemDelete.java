package com.bsb.permit.gui.menu;

import com.bsb.permit.gui.PmConfirmationDialog;
import com.bsb.permit.gui.PmPermitView;
import com.bsb.permit.gui.PmTabbedPermitView;

public class PmMenuItemDelete extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2897359128538391343L;

//	private static Logger logger = LoggerFactory.getLogger(PmMenuItemObsolete.class);

	public PmMenuItemDelete() {
		super();
	}

	public PmMenuItemDelete(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub

		if (PmConfirmationDialog.show("Are you sure to delete the permit?").isConfirmed()) {
			System.out.println("Yes");
			PmPermitView currentPermitView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
			currentPermitView.getSelectedRows();
		}
//		Thread t = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				PmPermitView currentPermitView = (PmPermitView) PmTabbedPermitView.getInstance().getSelectedComponent();
//				List<Integer> rows = currentPermitView.getSelectedRows();
//				List<Permit> permits = new ArrayList<Permit>(); // PmPermitView.getInstance().getTableModel().get
//				for (Integer row : rows) {
//					permits.add(new Permit(currentPermitView.getTableModel().getValueAt(row, 0).toString(),
//							currentPermitView.getTableModel().getValueAt(row, 1).toString(),
//							currentPermitView.getTableModel().getValueAt(row, 2).toString(),
//							StandardPermitStatuses
//									.mapPermitStatus(currentPermitView.getTableModel().getValueAt(row, 3).toString()),
//							currentPermitView.getPermitType(), PmShipView.getInstance().getSelectedImo())); // TODO
//				}
//				DataAccessor accessor = new DataAccessor();
//				try {
//					accessor.obsoletePermits(permits);
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
