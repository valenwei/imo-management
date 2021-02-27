package com.bsb.permit.gui.menu;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.dao.DataAccessor;

public class PmMenuItemExport extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2897359128538391343L;

	private static Logger logger = LoggerFactory.getLogger(PmMenuItemExport.class);

	private class ExportRunnable implements Runnable {

		private String fileName;

		public ExportRunnable(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logger.info("Start exporting, please wait...");
			DataAccessor accessor = new DataAccessor();
			try {
				Path path = Paths.get(fileName);
				BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
				writer.write(":DATE " + getCurrentDate());
				writer.newLine();
				writer.write(":VERSION 2");
				writer.newLine();
				writer.write(":ENC");
				writer.newLine();

				List<String> permits = accessor.exportRawData();
				for (String p : permits) {
					writer.write(p);
					writer.newLine();
				}
				writer.write(":ECS");
				writer.newLine();
				writer.close();

				logger.info("Succeed exporting");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("Failed exporting");
			} finally {
				try {
					accessor.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public PmMenuItemExport() {
		super();
	}

	public PmMenuItemExport(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub

		JFileChooser c = new JFileChooser();
		c.setDialogTitle("Select File");
		int ret = c.showSaveDialog(null);
		if (ret != 0) {
			logger.info("Cancel exporting");
			return;
		}

		File file = c.getSelectedFile();
		if (null == file) {
			return;
		}

		if (file.isDirectory()) {
			return;
		}

		if (file.exists()) {
			return;
		}

		final String fileName = c.getSelectedFile().getPath();

		Thread t = new Thread(new ExportRunnable(fileName));
		t.start();
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

		// Sapce
		sb.append(" ");

		// Hour
		if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
			sb.append("0");
		}
		sb.append(calendar.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		// Minute
		if (calendar.get(Calendar.MINUTE) < 10) {
			sb.append("0");
		}
		sb.append(calendar.get(Calendar.MINUTE));
		return sb.toString();
	}

}
