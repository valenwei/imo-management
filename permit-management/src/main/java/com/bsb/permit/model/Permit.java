package com.bsb.permit.model;

public class Permit {

	private String permitId;
	private String expireDate;
	private String rawText;
	private PermitStatus status;
	private PermitType permitType;
	private String imo;

	public Permit(String permitId, String expireDate, String rawText, PermitStatus status, PermitType permitType,
			String imo) {
		this.permitId = permitId;
		this.expireDate = expireDate;
		this.rawText = rawText;
		this.status = status;
		this.permitType = permitType;
		this.imo = imo;
	}

	public String getPermitId() {
		return permitId;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public String getRawText() {
		return rawText;
	}

	public PermitStatus getStatus() {
		return status;
	}

	public PermitType getPermitType() {
		return permitType;
	}

	public String getImo() {
		return imo;
	}

}
