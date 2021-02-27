package com.bsb.permit.model;

final class PermitStatusNew implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "New";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
