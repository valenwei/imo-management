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
		if (column == 1) {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = f.parse(value.toString());
				Date twoMonthLater = dateAddMonth(f.parse(getCurrentDate()), 2);

				if (date.compareTo(twoMonthLater) <= 0) {
					cell.setBackground(Color.RED);
				} else {
					cell.setBackground(Color.WHITE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String dateValue = table.getValueAt(row, 1).toString();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = f.parse(dateValue.toString());
				Date twoMonthLater = dateAddMonth(f.parse(getCurrentDate()), 2);

				if (date.compareTo(twoMonthLater) <= 0) {
					cell.setBackground(Color.RED);
				} else {
					cell.setBackground(Color.WHITE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
