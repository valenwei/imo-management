package com.bsb.permit.util;

public final class Constants {

	private Constants() {

	}

	public static final String PM_MENU_PERMIT = "Permit";
	public static final String PM_MENU_PERMIT_IMPORT = "Import";
	public static final String PM_MENU_PERMIT_MASTER = "Master";
	public static final String PM_MENU_PERMIT_BACKUP = "Backup";
	public static final String PM_MENU_PERMIT_RESERVE1 = "Reserve1";
	public static final String PM_MENU_PERMIT_RESERVE2 = "Reserve2";
	public static final String PM_MENU_PERMIT_EXPORT = "Export...";
	public static final String PM_MENU_PERMIT_DELETE = "Delete";
	public static final String PM_MENU_EXIT = "Exit";

	public static final String PM_MENU_SHIP = "Ship";
	public static final String PM_MENU_SHIP_ADD = "Add...";
	public static final String PM_MENU_SHIP_DELETE = "Delete";

	public static final String PM_MENU_VIEW = "View";
	public static final String PM_MENU_SHOW = "Switch View";

	public static final String PM_MENU_HELP = "Help";
	public static final String PM_MENU_ABOUT = "About...";

	public static final String PM_PERMIT_TYPE_MASTER = "MASTER";
	public static final String PM_PERMIT_TYPE_RESERVE1 = "RESERVE1";
	public static final String PM_PERMIT_TYPE_RESERVE2 = "RESERVE2";
	public static final String PM_PERMIT_TYPE_BACKUP = "BACKUP";
	public static final String PM_PERMIT_TYPE_NULL = "NULL";
	public static final String PM_PERMIT_EXPIRATION_STATUS_EXPIRED = "Expired";
	public static final String PM_PERMIT_EXPIRATION_STATUS_EXPIRING = "Expiring in a month";
	public static final String PM_PERMIT_EXPIRATION_STATUS_VALID = "Valid";

	public static final int DATA_ACCESSOR_ADD = 0;
	public static final int DATA_ACCESSOR_UPDATE = 1;
	public static final int DATA_ACCESSOR_SKIP = 2;
	public static final int DATA_ACCESSOR_FAILURE = -1;

	public static final String PM_SHIP_DUMMY = "PM-DummyShip";

	public static final String PM_EXPORT_FOLDER_PREFIX = "AVCSPermits-";

	// PmExceptions
	public static final String PM_EXCEPTION_CONNECTION_FAILURE = "Fail to connect to DB";
}
