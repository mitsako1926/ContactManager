package com.mitsako1926.contactManager.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public final class ContactManagerFrame extends JFrame{

	
	public ContactManagerFrame(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600,500));
		add(new ContactManagerPanel());

		pack();
		setVisible(true);
	}
}
