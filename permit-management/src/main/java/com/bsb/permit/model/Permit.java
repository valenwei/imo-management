package com.bsb.permit.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bsb.permit.util.Constants;
import com.bsb.permit.util.PmUtil;

public class Permit {

	private String permitId;
	private String expireDate;
	private String rawText;
	private String importDate;
	private PermitType permitType;
	private String imo;

	public Permit(String permitId, String expireDate, String rawText, String importDate, PermitType permitType,
			String imo) {
		this.permitId = permitId;
		this.expireDate = expireDate;
		this.rawText = rawText;
		this.importDate = importDate;
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

	public String getImportDate() {
		return importDate;
	}

	public PermitType getPermitType() {
		return permitType;
	}

	public String getImo() {
		return imo;
	}

	public String getExpirationStatus() {
		String expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_VALID;
		String dateValue = this.getExpireDate();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		try {
			Calendar calendar = Calendar.getInstance();

			Date date = f.parse(dateValue.toString());
			calendar.setTime(date);
			int expireYear = calendar.get(Calendar.YEAR);
			int expireMonth = calendar.get(Calendar.MONTH);

			Date currentDate = f.parse(PmUtil.getCurrentDate());
			calendar.setTime(currentDate);
			int currentYear = calendar.get(Calendar.YEAR);
			int currentMonth = calendar.get(Calendar.MONTH);

			if (expireYear < currentYear) {
				expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_EXPIRED;
			} else if (expireYear == currentYear) {
				if (expireMonth < currentMonth) {
					expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_EXPIRED;
				} else if (expireMonth == currentMonth) {
					expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_EXPIRING;
				} else {
					expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_VALID;
				}
			} else {
				expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_VALID;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			expirationStatus = Constants.PM_PERMIT_EXPIRATION_STATUS_VALID;
		}

		return expirationStatus;
	}
}
