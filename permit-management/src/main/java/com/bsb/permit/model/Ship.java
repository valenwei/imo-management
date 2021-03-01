package com.bsb.permit.model;

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
}
