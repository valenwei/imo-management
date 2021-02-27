package com.bsb.permit.model;

import com.bsb.permit.util.Constants;

final class PermitTypeReserve implements PermitType {

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return Constants.PM_PERMIT_TYPE_RESERVE;
	}

	@Override
	public String toString() {
		return this.getTypeName();
	}
}
