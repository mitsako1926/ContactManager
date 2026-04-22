package com.mitsako1926.contactManager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import com.mitsako1926.contactManager.model.Contact;
import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerRightPanel extends JPanel{

	
	private final ContactManagerRightEmptyPanel emptyPanel = new ContactManagerRightEmptyPanel();
	
	private final ContactManagerRightDetailsPanel detailsPanel = new ContactManagerRightDetailsPanel();
	
	private final ContactManagerRightAddOrUpdatePanel addPanel = new ContactManagerRightAddOrUpdatePanel();
	
	private final CardLayout cardLayout = new CardLayout();
	
	
	public ContactManagerRightPanel(ContactService service){
		
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(cardLayout);
		
		service.setRightPanel(this);
		
		addPanel.setService(service);
		
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
	
	
	
	public void resetAddPanel() {
		addPanel.setDefaultState();
	}
	
	
	
	public void setAddToUpdatePanel(Contact contact) {
		addPanel.setContactState(contact);
	}
	
	
	
	public Contact getContact() {
		
		if(detailsPanel.getContact()==null) {
			return null;
		}
		return detailsPanel.getContact();
		
	}
	
	
	
}
