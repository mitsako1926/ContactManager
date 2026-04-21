package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerFrame extends JFrame{

	
	public ContactManagerFrame(){
		
		//FRAME ICON
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/icons/frameIcon.png"));
		Image img = imageIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Contact Manager");
		setIconImage(img);
		setLayout(new BorderLayout());
		
		
		ContactService service = new ContactService();
		
		add(new ContactManagerPanel(service),BorderLayout.CENTER);
		add(new ContactManagerTopPanel(service),BorderLayout.NORTH);
		
		
		pack();
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (screenSize.width - getWidth()) / 2 ;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);

	}
	
	
	
}
