package com.bsb.permit.model;

final class PermitStatusObsoleted implements PermitStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Obsoleted";
	}

	@Override
	public String toString() {
		return this.getStatus();
	}
}
