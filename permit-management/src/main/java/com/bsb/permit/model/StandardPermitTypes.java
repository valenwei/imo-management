package com.bsb.permit.model;

public final class StandardPermitTypes {

	public static final PermitType TYPE_MASTER = new PermitTypeMaster();
	public static final PermitType TYPE_RESERVE = new PermitTypeReserve1();
	public static final PermitType TYPE_BACKUP = new PermitTypeBackup();
	private static final PermitType TYPE_NULL = new PermitTypeNull();

	private StandardPermitTypes() {

	}

	public static PermitType mapPermitType(String typeName) {
		if (null == typeName) {
			return TYPE_NULL;
		}

		if (typeName.equalsIgnoreCase(TYPE_MASTER.getTypeName())) {
			return TYPE_MASTER;
		}

		if (typeName.equalsIgnoreCase(TYPE_RESERVE.getTypeName())) {
			return TYPE_RESERVE;
		}

		if (typeName.equalsIgnoreCase(TYPE_BACKUP.getTypeName())) {
			return TYPE_BACKUP;
		}

		return TYPE_NULL;
	}
}
