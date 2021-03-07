package com.bsb.permit.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PmPermitViewCellRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7905580763004874520L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String dateValue = table.getValueAt(row, 1).toString();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		try {
			Calendar calendar = Calendar.getInstance();

			Date date = f.parse(dateValue.toString());
			calendar.setTime(date);
			int expireYear = calendar.get(Calendar.YEAR);
			int expireMonth = calendar.get(Calendar.MONTH);

			Date currentDate = f.parse(getCurrentDate());
			calendar.setTime(currentDate);
			int currentYear = calendar.get(Calendar.YEAR);
			int currentMonth = calendar.get(Calendar.MONTH);

//			Date twoMonthLater = dateAddMonth(f.parse(getCurrentDate()), 2);
			if (expireYear < currentYear) {
				cell.setBackground(Color.GRAY);
			} else if (expireYear == currentYear) {
				if (expireMonth < currentMonth) {
					cell.setBackground(Color.GRAY);
				} else if (expireMonth == currentMonth) {
					cell.setBackground(Color.YELLOW);
				} else {

					cell.setBackground(Color.WHITE);
//					Date sixMonthLater = dateAddMonth(currentDate, 6);
//					if (date.compareTo(sixMonthLater) < 0) {
//						cell.setBackground(Color.RED);
//					} else {
//						cell.setBackground(Color.WHITE);
//					}
				}
			} else {
				Date sixMonthLater = dateAddMonth(currentDate, 6);
				if (date.compareTo(sixMonthLater) < 0) {
					cell.setBackground(Color.RED);
				} else {
					cell.setBackground(Color.WHITE);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cell;
	}

	private String getCurrentDate() {
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

	private Date dateAddMonth(Date date, int month) throws Exception {

		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, month);// 日期加3个月
		// rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
		return rightNow.getTime();
	}
}
