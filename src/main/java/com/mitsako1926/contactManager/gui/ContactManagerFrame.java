package com.mitsako1926.contactManager.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class ContactManagerFrame extends JFrame{

	
	public ContactManagerFrame(){
		
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/icons/frameIcon.png"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Contact Manager");
		setIconImage(imageIcon.getImage());
		
		add(new ContactManagerPanel());
		
		pack();
		
		int x = (screenSize.width - getWidth()) / 2 ;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);

	}
}
