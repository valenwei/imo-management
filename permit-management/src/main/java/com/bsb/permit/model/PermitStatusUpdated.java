package com.bsb.permit.model;

final class PermitStatusUpdated implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Updated";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
