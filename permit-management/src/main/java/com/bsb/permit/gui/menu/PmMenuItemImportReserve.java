package com.bsb.permit.gui.menu;

import com.bsb.permit.model.PermitType;
import com.bsb.permit.model.StandardPermitTypes;

public class PmMenuItemImportReserve extends PmMenuItemImport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1147451573716025677L;

	public PmMenuItemImportReserve() {
		super();
	}

	public PmMenuItemImportReserve(String text) {
		super(text);
	}

	@Override
	public PermitType getPermitType() {
		// TODO Auto-generated method stub
		return StandardPermitTypes.TYPE_RESERVE;
	}

}
