package com.bsb.permit.model;

public class Ship {

	private String shipName;
	private String description;
	private String imo;
	private String ownerCompany;

	public Ship(String shipName, String description, String imo, String ownerCompany) {
		this.shipName = shipName;
		this.description = description;
		this.imo = imo;
		this.ownerCompany = ownerCompany;
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

}
