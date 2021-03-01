package com.bsb.permit.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PmShipDialogAdd extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353449955389795947L;
	private static final String DIALOG_TITLE = "Add Ship";
	private static final String SHIPNAME = "Ship Name: ";
	private static final String DESCIPTION = "Description: ";
	private static final String IMO = "IMO: ";
	private static final String OWNERCOMPANY = "Owner Company: ";
	private static final String MASTER = "Master: ";
	private static final String BACKUP = "Backup: ";
	private static final String RESERVE1 = "Reserve1";
	private static final String RESERVE2 = "Reserve2";
	private final Map<String, JTextField> txtFields = new HashMap<String, JTextField>();

	public PmShipDialogAdd(JFrame parent) {
		super(parent, DIALOG_TITLE, true);
//		Dimension size = parent.getSize();
		setBounds(parent.getX() + 100, parent.getY() + 100, 800, 400);
		init();
	}

	private void init() {
		this.setLayout(null);

		Container container = this.getContentPane();

		String[] labelTexts = new String[] { IMO, SHIPNAME, DESCIPTION, OWNERCOMPANY, MASTER, BACKUP, RESERVE1,
				RESERVE2 };

		for (int i = 0; i < labelTexts.length; i++) {
			JLabel lbl = new JLabel(labelTexts[i]);
			container.add(lbl);
			lbl.setBounds(40, 4 + 26 * i, 200, 25);
		}

		for (int i = 0; i < labelTexts.length; i++) {
			JTextField txtField = new JTextField(labelTexts[i]);
			container.add(txtField);
			txtField.setBounds(160, 4 + 26 * i, 550, 25);
			txtFields.put(labelTexts[i], txtField);
		}

		JButton btnOk = new JButton("OK");
		container.add(btnOk);
		btnOk.setBounds(250, 260, 80, 25);
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PmShipDialogAdd.this.ok();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		container.add(btnCancel);
		btnCancel.setBounds(470, 260, 80, 25);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PmShipDialogAdd.this.cancel();
			}
		});
	}

	private void ok() {
		System.out.println("OK");
		this.dispose();
	}

	private void cancel() {
		System.out.println("Cancel");
		this.dispose();
	}
}
