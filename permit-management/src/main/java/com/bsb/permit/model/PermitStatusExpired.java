package com.bsb.permit.model;

final class PermitStatusExpired implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Expired";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
