package com.bsb.permit.model;

final class PermitStatusNull implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Null";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
