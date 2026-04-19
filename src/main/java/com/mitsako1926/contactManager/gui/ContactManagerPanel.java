package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerPanel extends JPanel{

	
	public ContactManagerPanel() {
		setPreferredSize(new Dimension(700,500));
		setLayout(new BorderLayout());
		setOpaque(true);
		
		
		ContactService service = new ContactService();
		
		ContactManagerSideBarPanel sideBarPanel = new ContactManagerSideBarPanel(service);
		
		ContactManagerCenterPanel centerPanel = new ContactManagerCenterPanel(service);
		
		ContactManagerRightPanel rightPanel = new ContactManagerRightPanel(service);
		
		
		add(sideBarPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
}
