package com.bsb.permit.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PmMenuItem extends JMenuItem implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2421029234205776870L;

	private static Logger logger = LoggerFactory.getLogger(PmMenuItem.class);
	
	public PmMenuItem() {
		super();
		this.addActionListener(this);
	}
	
	public PmMenuItem(String text) {
		super(text);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.getClass().getName().startsWith("com.bsb.permit.gui.menu.PmMenuItem")) {
			logger.info("Menu '" + o.getClass().getSimpleName() + "' is clicked.");
			PmMenuItem mi = (PmMenuItem) o;
			mi.performAction();
		}
	}
	
	public abstract void performAction() ;
}
