package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerSideBarPanel extends JPanel{

	private final JButton allContactsButton, favoriteContactsButton, addContactButton;
	
	private final JPanel panelButtons = new JPanel(new GridLayout(3, 1, 10, 15));
	
	private final ContactService service;
	
	private JButton selectedButton,previousButton;
	

	
	public ContactManagerSideBarPanel(ContactService service){
		setBackground(Color.decode("#F7F9FC"));
		setPreferredSize(new Dimension(150,500));
		setLayout(new BorderLayout());
		setOpaque(true);
		
		this.service = service;
		
		service.setSideBarPanel(this);
		
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
		
		setSelectedButton(allContactsButton);
		
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
		button.setBorder(
			    BorderFactory.createCompoundBorder(
			        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#D6DEE8")),
			        BorderFactory.createEmptyBorder(0, 10, 0, 0)
			    )
			);
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(240,240,240));
		
		button.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        if (button != selectedButton) {
		            button.setBackground(new Color(220,220,220));
		        }
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        if (button != selectedButton) {
		            button.setBackground(new Color(240,240,240));
		        }
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
	    if(e.getSource() == allContactsButton) {
	        setSelectedButton(allContactsButton);
	        service.loadAllContacts();
	        
	    } else if(e.getSource() == favoriteContactsButton) {
	        setSelectedButton(favoriteContactsButton);
	        service.loadFavoriteContacts();
	        
	    } else if(e.getSource() == addContactButton){
	        setSelectedButton(addContactButton);
	        service.addContact();
	    }
	}
	
	
	
	private void setSelectedButton(JButton button) {
	    resetButtonStyle(allContactsButton);
	    resetButtonStyle(favoriteContactsButton);
	    resetButtonStyle(addContactButton);

	    button.setBackground(Color.WHITE);
	    button.setBorder(
	        BorderFactory.createCompoundBorder(
	            BorderFactory.createMatteBorder(0, 3, 1, 0, Color.decode("#3B82F6")),
	            BorderFactory.createEmptyBorder(0, 10, 0, 0)
	        )
	    );
	    
	    previousButton = selectedButton;
	    selectedButton = button;
	    
	}
	
	
	
	private void resetButtonStyle(JButton button) {
	    button.setBackground(new Color(240,240,240));
	    button.setBorder(
	        BorderFactory.createCompoundBorder(
	            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#D6DEE8")),
	            BorderFactory.createEmptyBorder(0, 10, 0, 0)
	        )
	    );
	}
	
	
	
	public JButton getPreviousButton() {
		return previousButton;
	}
	
	
	
	public JButton getSelectedButton() {
		return selectedButton;
	}
	
	
	
	public void selectButton(JButton button) {
		setSelectedButton(button);
	}
}
