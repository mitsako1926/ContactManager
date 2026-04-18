package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public final class ContactManagerPanel extends JPanel{

	
	public ContactManagerPanel() {
		setPreferredSize(new Dimension(700,500));
		setLayout(new BorderLayout());
		setOpaque(true);
		
		add(new ContactManagerSideBarPanel(), BorderLayout.WEST);
		add(new ContactManagerCenterPanel(), BorderLayout.CENTER);
		add(new ContactManagerRightPanel(), BorderLayout.EAST);
	}
}
