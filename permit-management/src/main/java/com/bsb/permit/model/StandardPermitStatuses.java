package com.bsb.permit.model;

public final class StandardPermitStatuses {

	public static final PermitStatus STATUS_NEW = new PermitStatusNew();
	public static final PermitStatus STATUS_UPDATED = new PermitStatusUpdated();
	public static final PermitStatus STATUS_EXTENDED = new PermitStatusExtended();
	public static final PermitStatus STATUS_OBSOLETED = new PermitStatusObsoleted();
	public static final PermitStatus STATUS_EXPIRED = new PermitStatusExpired();
	private static final PermitStatus STATUS_NULL = new PermitStatusNull();

	private StandardPermitStatuses() {

	}

	public static PermitStatus mapPermitStatus(String status) {
		if (null == status) {
			return STATUS_NULL;
		}

		if (status.equalsIgnoreCase(STATUS_NEW.getStatus())) {
			return STATUS_NEW;
		}

		if (status.equalsIgnoreCase(STATUS_UPDATED.getStatus())) {
			return STATUS_UPDATED;
		}

		if (status.equalsIgnoreCase(STATUS_EXTENDED.getStatus())) {
			return STATUS_EXTENDED;
		}

		if (status.equalsIgnoreCase(STATUS_OBSOLETED.getStatus())) {
			return STATUS_OBSOLETED;
		}

		if (status.equalsIgnoreCase(STATUS_EXPIRED.getStatus())) {
			return STATUS_EXPIRED;
		}

		return STATUS_NULL;
	}
}
