package com.mitsako1926.contactManager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class ContactManagerRightEmptyPanel extends JPanel{

	
	
	public ContactManagerRightEmptyPanel() {
		
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(new GridBagLayout());
		setBackground(Color.decode("#F7F9FC"));
		
		JLabel labelEmpty = new JLabel("<html><div style='text-align: center;'>"
			    + "No contact selected<br>"
			    + "Select a contact or click \"Add Contact\""
			    + "</div></html>");
		
		customizeLabel(labelEmpty);
		
		add(labelEmpty);
	
	}
	
	
	private void customizeLabel(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground(Color.DARK_GRAY);
		label.setOpaque(true);
		label.setBackground(Color.decode("#F7F9FC"));
		label.setPreferredSize(new Dimension(200,100));
	}
}
