package com.bsb.permit.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.bsb.permit.util.Constants;

public class PmMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1352743412239863094L;

	public PmMenuBar() {
		this.init();
	}

	private void init() {

		// Permit menu --------------------------------------------------------------
		JMenu fileMenu = new JMenu(Constants.PM_MENU_PERMIT);
		fileMenu.setMnemonic(KeyEvent.VK_P);

		JMenu importMenu = new JMenu(Constants.PM_MENU_PERMIT_IMPORT);
		importMenu.setMnemonic(KeyEvent.VK_I);
		PmMenuItem importMasterMenu = new PmMenuItemImportMaster("Master");
		importMasterMenu.setMnemonic(KeyEvent.VK_M);
		importMenu.add(importMasterMenu);

		PmMenuItem importReserveMenu = new PmMenuItemImportReserve1("Reserve");
		importReserveMenu.setMnemonic(KeyEvent.VK_R);
		importMenu.add(importReserveMenu);

		PmMenuItem importBackupMenu = new PmMenuItemImportBackup("Backup");
		importBackupMenu.setMnemonic(KeyEvent.VK_B);
		importMenu.add(importBackupMenu);

		PmMenuItem export = new PmMenuItemExport(Constants.PM_MENU_PERMIT_EXPORT);
		export.setMnemonic(KeyEvent.VK_E);

		PmMenuItem obsolete = new PmMenuItemObsolete(Constants.PM_MENU_PERMIT_OBSOLETE);
		obsolete.setMnemonic(KeyEvent.VK_T);

		PmMenuItem exit = new PmMenuItemExit(Constants.PM_MENU_EXIT);
		exit.setMnemonic(KeyEvent.VK_X);

		fileMenu.add(importMenu);
		fileMenu.add(export);
		fileMenu.add(obsolete);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		this.add(fileMenu);

		// View menu --------------------------------------------------------------
		JMenu viewMenu = new JMenu(Constants.PM_MENU_VIEW);
		viewMenu.setMnemonic(KeyEvent.VK_V);

		PmMenuItem show = new PmMenuItemShow(Constants.PM_MENU_SHOW);
		show.setMnemonic(KeyEvent.VK_W);

		viewMenu.add(show);

		this.add(viewMenu);

		// Ship menu
		JMenu shipMenu = new JMenu(Constants.PM_MENU_SHIP);
		shipMenu.setMnemonic(KeyEvent.VK_S);

		PmMenuItem addShip = new PmMenuItemAddShip(Constants.PM_MENU_SHIP_ADD);
		shipMenu.add(addShip);

		PmMenuItem deleteShip = new PmMenuItemAddShip(Constants.PM_MENU_SHIP_DELETE);
		shipMenu.add(deleteShip);

		this.add(shipMenu);

		// Help menu --------------------------------------------------------------
		JMenu helpMenu = new JMenu(Constants.PM_MENU_HELP);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		PmMenuItem about = new PmMenuItemAbout(Constants.PM_MENU_ABOUT);
		about.setMnemonic(KeyEvent.VK_A);
		helpMenu.add(about);

		this.add(helpMenu);
	}
}
