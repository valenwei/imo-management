package com.bsb.permit.gui.menu;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.gui.PmShipView;
import com.bsb.permit.model.Ship;
import com.bsb.permit.util.Constants;

public class PmMenuItemExport extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2897359128538391343L;

	private static Logger logger = LoggerFactory.getLogger(PmMenuItemExport.class);

	private class ExportRunnable implements Runnable {

		private Ship ship;
		private String path;

		public ExportRunnable(Ship ship, String path) {
			this.ship = ship;
			this.path = path;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logger.info("Start exporting, please wait...");

			Map<String, String> folders = createExportFolders();

			for (String key : folders.keySet()) {
				new Thread(new ExportExecuter(this.ship, folders.get(key), key)).start();
			}
		}

		private Map<String, String> createExportFolders() {
			Map<String, String> folders = new HashMap<String, String>();
			if (null == this.ship) {
				return folders;
			}

			String folderPath = this.path;
			if (createFolder(folderPath)) {

				if (!folderPath.endsWith(File.separator)) {
					folderPath = folderPath + File.separator;
				}
				folderPath = folderPath + Constants.PM_EXPORT_FOLDER_PREFIX + System.currentTimeMillis();
				if (createFolder(folderPath)) {
					if (!folderPath.endsWith(File.separator)) {
						folderPath = folderPath + File.separator;
					}
					if (ship.hasMaster() && createFolder(folderPath + Constants.PM_PERMIT_TYPE_MASTER)) {
						folders.put(Constants.PM_PERMIT_TYPE_MASTER, folderPath + Constants.PM_PERMIT_TYPE_MASTER);
					}

					if (ship.hasBackup() && createFolder(folderPath + Constants.PM_PERMIT_TYPE_BACKUP)) {
						folders.put(Constants.PM_PERMIT_TYPE_BACKUP, folderPath + Constants.PM_PERMIT_TYPE_BACKUP);
					}

					if (ship.hasReserve1() && createFolder(folderPath + Constants.PM_PERMIT_TYPE_RESERVE1)) {
						folders.put(Constants.PM_PERMIT_TYPE_RESERVE1, folderPath + Constants.PM_PERMIT_TYPE_RESERVE1);
					}

					if (ship.hasReserve2() && createFolder(folderPath + Constants.PM_PERMIT_TYPE_RESERVE2)) {
						folders.put(Constants.PM_PERMIT_TYPE_RESERVE2, folderPath + Constants.PM_PERMIT_TYPE_RESERVE2);
					}
				}
			}
			return folders;
		}

		private boolean createFolder(String path) {
			File dir = new File(path);
			if (dir.exists()) {
				return true;
			}
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
			//
			if (dir.mkdirs()) {
				return true;
			} else {
				return false;
			}
		}
	}

	private class ExportExecuter implements Runnable {

		private Ship ship;
		private String folder;
		private String permitType;

		public ExportExecuter(Ship ship, String folder, String permitType) {
			this.ship = ship;
			this.folder = folder;
			this.permitType = permitType;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DataAccessor accessor = new DataAccessor();
			try {
				if (!folder.endsWith(File.separator)) {
					folder = folder + File.separator;
				}
				Path path = Paths.get(folder + "PERMIT.TXT");
				BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
				writer.write(":DATE " + getCurrentDate());
				writer.newLine();
				writer.write(":VERSION 2");
				writer.newLine();
				writer.write(":ENC");
				writer.newLine();

				List<String> permits = accessor.exportRawData(this.ship, this.permitType);
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

		Ship ship = PmShipView.getInstance().getSelectedShip();
		if (null == ship) {
			return;
		}

		JFileChooser c = new JFileChooser();
		c.setDialogTitle("Select Folder");
		c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
			System.out.println(c.getSelectedFile().getPath());

			final String fileName = c.getSelectedFile().getPath();
			//
			Thread t = new Thread(new ExportRunnable(ship, fileName));
			t.start();
			return;
		}
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
