package com.bsb.permit.gui.menu;

public class PmMenuItemExit extends PmMenuItem {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8290674394405255889L;

	public PmMenuItemExit() {
		super();
	}
	
	public PmMenuItemExit(String text) {
		super(text);
	}
	
	@Override
	public void performAction() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}
