package com.bsb.permit.model;

final class PermitStatusExtended implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Extended";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
