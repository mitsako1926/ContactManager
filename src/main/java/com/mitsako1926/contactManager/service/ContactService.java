package com.mitsako1926.contactManager.service;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.mitsako1926.contactManager.dao.ContactDAO;
import com.mitsako1926.contactManager.gui.ContactManagerCenterPanel;
import com.mitsako1926.contactManager.gui.ContactManagerRightPanel;
import com.mitsako1926.contactManager.gui.ContactManagerSideBarPanel;
import com.mitsako1926.contactManager.model.Contact;

public final class ContactService {

	
	private final ContactDAO contactDAO = new ContactDAO();
	
	private ContactManagerSideBarPanel sideBarPanel;
	
	private ContactManagerCenterPanel centerPanel;
		
	private ContactManagerRightPanel rightPanel;
	
	private List<Contact> contacts = contactDAO.getAllContacts();;
	
	
	public void setSideBarPanel(ContactManagerSideBarPanel sideBarPanel) {
		this.sideBarPanel = sideBarPanel;
	}
	
	
	
	public void setCenterPanel(ContactManagerCenterPanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	
	
	
	public void setRightPanel(ContactManagerRightPanel rightPanel) {
		this.rightPanel = rightPanel;
	}
	
	
	
	public List<Contact> getContacts(){
		return contacts;
	}
	
	
	
	public void loadAllContacts(){
		contacts = contactDAO.getAllContacts();
		centerPanel.callRefresh();
		rightPanel.showEmpty();
		rightPanel.detailsOfContact(null);
	}
	
	
	
	public void loadFavoriteContacts(){
		contacts = contactDAO.getFavoriteContacts();
		centerPanel.callRefresh();	
		rightPanel.showEmpty();
		rightPanel.detailsOfContact(null);
	}
	
	
	
	public List<Contact> searchContacts(String keyword){
		return contactDAO.searchContacts(keyword);
	}
	
	
	
	public void sortByFirstName(){
		JButton previousButton = sideBarPanel.getPreviousButton();
		JButton selectedButton = sideBarPanel.getSelectedButton();
		
		if(selectedButton.getText().equals("All Contacts") || (selectedButton.getText().equals("Add Contact") && previousButton.getText().equals("All Contacts"))) {
			contacts = contactDAO.getAllContactsOrderByFirstName();
		}else contacts = contactDAO.getFavoriteContactsOrderByFirstName();
		
	}
	
	
	
	public void sortByLastName(){
		JButton previousButton = sideBarPanel.getPreviousButton();
		JButton selectedButton = sideBarPanel.getSelectedButton();
		
		if(selectedButton.getText().equals("All Contacts") || (selectedButton.getText().equals("Add Contact") && previousButton.getText().equals("All Contacts"))) {
			contacts = contactDAO.getAllContactsOrderByLastName();
		}else contacts = contactDAO.getFavoriteContactsOrderByLastName();
		
	}
	
	
	
	public void sortFavoritesFirst(){
		contacts = contactDAO.getAllContactsOrderByFavoriteFirst();
	}
	
	
	
	public void addContact() {
		rightPanel.detailsOfContact(null);
		
		if(centerPanel.getList().getSelectedValue()!=null) {
			centerPanel.getList().clearSelection();
		}
		rightPanel.showAdd();
		//contactDAO.addContact(contact);
	}
	
	
	
	public void getDetails(Contact contact) {
		rightPanel.showDetails();
		rightPanel.detailsOfContact(contact);
		
		JButton previousButton = sideBarPanel.getPreviousButton();
		JButton selectedButton = sideBarPanel.getSelectedButton();
		
		if(previousButton!=null && selectedButton.getText().equals("Add Contact")) {
			sideBarPanel.selectButton(previousButton);
		}
			
		
	}
	
	
	
	public void updateContact(Contact contact) {
		contactDAO.updateContact(contact);
	}
	
	
	
	public void deleteContact() {		
		Contact contact = rightPanel.getContact();
		
		if(contact==null)return;
		
		Object selected = optionPane(contact);

		if ("Delete".equals(selected)) {
		    contactDAO.deleteContact(contact.getId());
		    
		    loadAllContacts();
		    
		    rightPanel.showEmpty();
		    rightPanel.detailsOfContact(null);
		}
	    
	}
	
	
	
	public void setFavorite() {
		Contact contact = centerPanel.getSelectedContact();
		
		if(contact==null)return;
		
		contactDAO.setFavorite(contact.getId(), !contact.isFavorite());
		
		contact.setFavorite(!contact.isFavorite());
		getDetails(contact);
	}
	
	
	
	private Object optionPane(Contact contact) {
		String fullName = contact.toString();
		
		String message = "<html><body style='font-size:12px;'>"
		        + "Are you sure you want to delete<br><b>" + fullName + "</b>?"
		        + "</body></html>";

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/icons/bin.png"));
	    Image img = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		
		JOptionPane optionPane = new JOptionPane(
		    message,
		    JOptionPane.PLAIN_MESSAGE,
		    JOptionPane.YES_NO_OPTION,
		    null,
		    new Object[]{"Delete", "Cancel"},
		    "Cancel"
		);
		
		JDialog dialog = optionPane.createDialog("Delete Contact");
		dialog.setIconImage(img);
		
		removeFocus(optionPane);

		dialog.setVisible(true);

		return optionPane.getValue();
		
	}
	
	
	
	private void removeFocus(Component comp) {
	    if (comp instanceof JButton btn) {
	        btn.setFocusable(false);
	    }

	    if (comp instanceof Container container) {
	        for (Component child : container.getComponents()) {
	            removeFocus(child);
	        }
	    }
	}
	
	
	
	
}
