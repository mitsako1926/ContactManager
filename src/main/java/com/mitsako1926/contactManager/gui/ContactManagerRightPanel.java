package com.mitsako1926.contactManager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.mitsako1926.contactManager.model.Contact;
import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerRightPanel extends JPanel{

	
	private final ContactManagerRightEmptyPanel emptyPanel = new ContactManagerRightEmptyPanel();
	
	private final ContactManagerRightDetailsPanel detailsPanel = new ContactManagerRightDetailsPanel();
	
	private final ContactManagerRightAddPanel addPanel = new ContactManagerRightAddPanel();
	
	private final CardLayout cardLayout = new CardLayout();
	
	private final ContactService service;
	
	public ContactManagerRightPanel(ContactService service){
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(cardLayout);
		
		this.service = service;
		service.setRightPanel(this);
		
		add(emptyPanel,"EMPTY");
		add(detailsPanel,"DETAILS");
		add(addPanel,"ADD");
		
		cardLayout.show(this,"EMPTY");
	}
	
	
	
	public void showEmpty() {
	    cardLayout.show(this, "EMPTY");
	}

	
	
	public void showDetails() {
	    cardLayout.show(this, "DETAILS");
	}

	
	
	public void showAdd() {
	    cardLayout.show(this, "ADD");
	}
	
	
	
	public void detailsOfContact(Contact contact) {
		detailsPanel.setContact(contact);
	}
	
	
}
