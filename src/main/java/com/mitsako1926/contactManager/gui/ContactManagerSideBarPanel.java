package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public final class ContactManagerSideBarPanel extends JPanel{

	private final JButton allContactsButton, favoriteContactsButton, addContactButton;
	
	private final JPanel panelButtons = new JPanel(new GridLayout(3, 1, 10, 15));
	
	public ContactManagerSideBarPanel(){
		setBackground(Color.black);
		setPreferredSize(new Dimension(150,500));
		setLayout(new BorderLayout());
		setOpaque(true);
		
		
		//GET THE IMAGES
		ImageIcon iconAll = new ImageIcon(getClass().getResource("/images/icons/contacts.png"));
		Image imgAll = iconAll.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		
		ImageIcon iconFav = new ImageIcon(getClass().getResource("/images/icons/favorite.png"));
		Image imgFav = iconFav.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		
		ImageIcon iconAdd = new ImageIcon(getClass().getResource("/images/icons/add-user.png"));
		Image imgAdd = iconAdd.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		//CUSTOMIZE THE BUTTONS
		allContactsButton = new JButton("All Contacts", new ImageIcon(imgAll));
		customizeButton(allContactsButton);
		
		favoriteContactsButton = new JButton("Favorites", new ImageIcon(imgFav));
		customizeButton(favoriteContactsButton);
		
		addContactButton = new JButton("Add Contact", new ImageIcon(imgAdd));
		customizeButton(addContactButton);
		
		//PANEL THAT HOLDS THE BUTTONS
		panelButtons.setPreferredSize(new Dimension(150,170));
		
		panelButtons.add(allContactsButton);
		panelButtons.add(favoriteContactsButton);
		panelButtons.add(addContactButton);
		
		//ADD THE PANEL TO THE SIDEBAR
		add(panelButtons, BorderLayout.NORTH);
		
	}
	
	
	
	private void customizeButton(JButton button) {
		button.setHorizontalAlignment(JButton.LEFT);
		button.setHorizontalTextPosition(JButton.RIGHT);
		button.setIconTextGap(10);
		button.setPreferredSize(new Dimension(120, 40));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		button.setBorderPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(240,240,240));
		
		button.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        button.setBackground(new Color(220,220,220));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        button.setBackground(new Color(240,240,240));
		    }
		});
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				press(e);
			}

			
			
		});
		
	}
	
	
	
	private void press(ActionEvent e) {
		if(e.getSource()==allContactsButton) {
			System.out.println(0);
		}else if(e.getSource()==favoriteContactsButton) {
			System.out.println(1);
		}else if(e.getSource()==addContactButton){
			System.out.println(2);
		}
	}
	
	
	
}
