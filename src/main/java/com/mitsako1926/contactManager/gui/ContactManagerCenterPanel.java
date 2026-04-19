package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.mitsako1926.contactManager.model.Contact;
import com.mitsako1926.contactManager.service.ContactService;


public final class ContactManagerCenterPanel extends JPanel{

	
	private final JPanel topPanel = new JPanel(new BorderLayout());
	private final JPanel contactsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	private final JLabel labelContacts = new JLabel("    All Contacts");
	private final JLabel labelSortBy = new JLabel("Sort By");
	
	private final DefaultListModel<Contact> model;
    
	private final JList<Contact> list;
	
	private final JComboBox<String> comboBox = new JComboBox<String>();
	
	private final ContactService service;
	
	ContactManagerCenterPanel(ContactService service){
		setPreferredSize(new Dimension(250,500));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.decode("#D6DEE8")));

		this.service = service;
		service.setCenterPanel(this);
		
		//TOP PANEL
		topPanel.setPreferredSize(new Dimension(300,40));
		
		comboBox.setPreferredSize(new Dimension(100,25));
		comboBox.addItem("First Name");
		comboBox.addItem("Last Name");
		comboBox.addItem("Favorites First");
		comboBox.setSelectedIndex(-1);
		
		comboBox.addActionListener(e -> {
		    String selected = (String) comboBox.getSelectedItem();

		    if (selected == null) return;

		    if (selected.equals("First Name")) {
		        service.sortByFirstName();
		    } else if (selected.equals("Last Name")) {
		        service.sortByLastName();
		    } else if (selected.equals("Favorites First")) {
		        service.sortFavoritesFirst();
		    }

		    refreshList();
		});
		
		JPanel panelSortBy = new JPanel(new FlowLayout(1,5,8));
		panelSortBy.add(labelSortBy);
		panelSortBy.add(comboBox);
		
		topPanel.add(labelContacts,BorderLayout.WEST);
		topPanel.add(panelSortBy,BorderLayout.EAST);
		
		
		contactsPanel.setOpaque(true);
		contactsPanel.setBackground(Color.decode("#F7F9FC"));
		contactsPanel.setLayout(new BorderLayout());
		contactsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		topPanel.setOpaque(true);
		topPanel.setBackground(Color.WHITE);
		
		
		//CONTACTS PANEL
		
		model = new DefaultListModel<Contact>();
		refreshList();
		
		list = new JList<Contact>(model);
		list.setCellRenderer(new ContactListRenderer());
		list.setFont(new Font("Arial", Font.PLAIN, 18));
		list.setBackground(Color.decode("#F7F9FC"));
		list.setForeground(Color.DARK_GRAY);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Dialog",Font.PLAIN,17));
		list.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	Contact selectedContact = list.getSelectedValue();

		            if (selectedContact != null) {
		                service.getDetails(selectedContact);
		            }
		        }
		    }
		});
		
		contactsPanel.add(new JScrollPane(list),BorderLayout.CENTER);
		
		//ADD EVERYTHING TO THE MAIN PANEL
		add(topPanel,BorderLayout.NORTH);
		add(contactsPanel,BorderLayout.CENTER);
	}
	
	
	
	private void refreshList() {
	    model.clear();
	    model.addAll(service.getContacts());
	}
	
	
	
	public void callRefresh() {
		refreshList();
	}
	
	
	
	public Contact getSelectedContact() {
		return list.getSelectedValue();
	}
	
	
	
	public JList<Contact> getList(){
		return list;
	}
}
