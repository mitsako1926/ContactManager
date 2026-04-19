package com.mitsako1926.contactManager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public final class ContactManagerRightPanel extends JPanel{

	private final ContactManagerRightEmptyPanel emptyPanel = new ContactManagerRightEmptyPanel();
	private final ContactManagerRightDetailsPanel detailsPanel = new ContactManagerRightDetailsPanel();
	private final ContactManagerRightAddPanel addPanel = new ContactManagerRightAddPanel();
	private final CardLayout cardLayout = new CardLayout();
	
	public ContactManagerRightPanel(){
		setBackground(Color.CYAN);
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(cardLayout);
		
		
		add(emptyPanel,"EMPTY");
		add(detailsPanel,"DETAILS");
		add(addPanel,"ADD");
		
		cardLayout.show(this,"DETAILS");
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
	
	
	
}
