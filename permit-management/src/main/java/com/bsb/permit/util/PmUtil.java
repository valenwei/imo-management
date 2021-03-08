package com.bsb.permit.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class PmUtil {

	private PmUtil() {

	}

	public static final String getCurrentDate() {
		StringBuilder sb = new StringBuilder();
		Calendar calendar = GregorianCalendar.getInstance();

		// Year
		sb.append(calendar.get(Calendar.YEAR));

		// Month, the value is 0-based
		if (calendar.get(Calendar.MONTH) < 9) {
			sb.append("0");
		}
		sb.append(calendar.get(Calendar.MONTH) + 1);

		// Day
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			sb.append("0");
		}
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));

		return sb.toString();
	}
}
