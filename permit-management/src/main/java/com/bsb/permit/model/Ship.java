package com.bsb.permit.model;

import com.bsb.permit.util.Constants;

public class Ship {

	private String shipName;
	private String description;
	private String imo;
	private String ownerCompany;
	private String master;
	private String backup;
	private String reserve1;
	private String reserve2;

	public Ship(String shipName, String description, String imo, String ownerCompany, String master, String backup,
			String reserve1, String reserve2) {
		this.shipName = shipName;
		this.description = description;
		this.imo = imo;
		this.ownerCompany = ownerCompany;
		this.master = master;
		this.backup = backup;
		this.reserve1 = reserve1;
		this.reserve2 = reserve2;
	}

	public String getShipName() {
		return this.shipName;
	}

	public String getDescription() {
		return this.description;
	}

	public String getImo() {
		return imo;
	}

	public String getOwnerCompany() {
		return ownerCompany;
	}

	public String getMaster() {
		return master;
	}

	public String getBackup() {
		return backup;
	}

	public String getReserve1() {
		return reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public boolean hasMaster() {
		if (null == this.master || 0 == master.length()) {
			return false;
		}

		return true;
	}

	public boolean hasBackup() {
		if (null == this.backup || 0 == backup.length()) {
			return false;
		}

		return true;
	}

	public boolean hasReserve1() {
		if (null == this.reserve1 || 0 == reserve1.length()) {
			return false;
		}

		return true;
	}

	public boolean hasReserve2() {
		if (null == this.reserve2 || 0 == reserve2.length()) {
			return false;
		}

		return true;
	}

	public boolean isDummyShip() {
		return Constants.PM_SHIP_DUMMY.equalsIgnoreCase(this.getShipName());
	}
}
