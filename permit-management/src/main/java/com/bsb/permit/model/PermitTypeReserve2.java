package com.bsb.permit.model;

import com.bsb.permit.util.Constants;

final class PermitTypeReserve2 implements PermitType {

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return Constants.PM_PERMIT_TYPE_RESERVE2;
	}

	@Override
	public String toString() {
		return this.getTypeName();
	}
}
