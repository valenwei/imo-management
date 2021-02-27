package com.bsb.permit.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.bsb.permit.gui.menu.PmMenuBar;

public class PmMainFrame extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568310779820634043L;
	private static PmMainFrame instance = null;

	public static synchronized void start() {
		if (null == instance) {
			instance = new PmMainFrame();
		}
		instance.setVisible(true);
	}

	public static synchronized void stop() {
		if (null != instance) {
			instance.setVisible(false);
			instance.dispose();
		}
	}

	public static synchronized PmMainFrame getInstance() {
		if (null == instance) {
			instance = new PmMainFrame();
		}
		return instance;
	}

	private PmMainFrame() {
		super();

		this.setJMenuBar(new PmMenuBar());

		this.initFrame();
	}

	private void initFrame() {
		this.setLayout(new BorderLayout());

		this.add(new PmSplitPane());

		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);

		this.setTitle("Permit Management");

		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
