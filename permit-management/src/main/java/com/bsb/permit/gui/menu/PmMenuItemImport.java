package com.bsb.permit.gui.menu;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsb.permit.dao.DataAccessor;
import com.bsb.permit.gui.PmMainFrame;
import com.bsb.permit.gui.PmPermitImportResultDialog;
import com.bsb.permit.gui.PmPermitView;
import com.bsb.permit.gui.PmShipView;
import com.bsb.permit.model.Permit;
import com.bsb.permit.model.PermitType;
import com.bsb.permit.model.Ship;
import com.bsb.permit.util.PmUtil;

public abstract class PmMenuItemImport extends PmMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8024549062000543202L;

	private static Logger logger = LoggerFactory.getLogger(PmMenuItemImport.class);

	private class TextFileFilter extends FileFilter {

		private final static String TXT = "txt";

		@Override
		public boolean accept(File f) {
			// TODO Auto-generated method stub
			if (f.isDirectory()) {
				return true;
			}

			String extension = this.getExtension(f);
			if (extension != null) {
				if (extension.equals(TXT)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "TXT (*.txt)";
		}

		private String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1) {
				ext = s.substring(i + 1).toLowerCase();
			}
			return ext;
		}
	}

	private class ImportRunnable implements Runnable {

		private String fileName;

		private ImportRunnable(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			PmPermitImportResultDialog result = new PmPermitImportResultDialog(PmMainFrame.getInstance());
			result.setModalityType(Dialog.ModalityType.MODELESS);
			result.setVisible(true);
			DataAccessor accessor = null;
			try {
				accessor = new DataAccessor();
				File fin = new File(fileName);
				FileInputStream fis = new FileInputStream(fin);

				BufferedReader br = new BufferedReader(new InputStreamReader(fis));

				Ship ship = PmShipView.getInstance().getSelectedShip();
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.length() < 64 || line.startsWith(":")) {
						logger.info("Line skipped: " + line);
					} else {
						Permit p = parseLine(line, ship);
						logger.debug(p.getPermitId());
						logger.debug(p.getExpireDate());
						logger.debug(p.getRawText());
						logger.debug(p.getPermitType().getTypeName());
						logger.debug("-------------------------------------------------");
						result.addPermit(p, accessor.addOrUpdatePermit(p));
					}
				}

				br.close();

				PmPermitView.getInstance(getPermitType().getTypeName()).showPermitsOfShip(ship, true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != accessor) {
					try {
						accessor.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	public PmMenuItemImport() {
		super();
	}

	public PmMenuItemImport(String text) {
		super(text);
	}

	@Override
	public void performAction() {
		// TODO Auto-generated method stub

		logger.info("Start importing...");
		try {
			JFileChooser c = new JFileChooser();
			c.setDialogTitle("Select File");
			c.addChoosableFileFilter(new TextFileFilter());
			int ret = c.showOpenDialog(null);
			if (0 == ret) {
				new Thread(new ImportRunnable(c.getSelectedFile().getPath())).start();
			} else {
				logger.info("Cancel importing");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Fail importing");
		}
	}

	private Permit parseLine(String line, Ship ship) {
		if (null == line || null == ship) {
			return new Permit("", "", "", PmUtil.getCurrentDate(), this.getPermitType(), ship.getImo());
		}

		try {
			String permitId = line.substring(0, 8);
			String expireDate = line.substring(8, 16);
			return new Permit(permitId, expireDate, line, PmUtil.getCurrentDate(), this.getPermitType(), ship.getImo());
		} catch (Exception e) {
			return new Permit("", "", "", PmUtil.getCurrentDate(), this.getPermitType(), ship.getImo());
		}
	}

	public abstract PermitType getPermitType();

}
