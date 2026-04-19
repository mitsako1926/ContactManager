package com.mitsako1926.contactManager.service;

import java.util.List;

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
	}
	
	
	
	public void loadFavoriteContacts(){
		contacts = contactDAO.getFavoriteContacts();
		centerPanel.callRefresh();	
	}
	
	
	
	public List<Contact> searchContacts(String keyword){
		return contactDAO.searchContacts(keyword);
	}
	
	
	
	public void sortByFirstName(){
		contacts = contactDAO.getAllContactsOrderByFirstName();
	}
	
	
	
	public void sortByLastName(){
		contacts = contactDAO.getAllContactsOrderByLastName();
	}
	
	
	
	public void sortFavoritesFirst(){
		contacts = contactDAO.getAllContactsOrderByFavoriteFirst();
	}
	
	
	
	public void addContact() {
		rightPanel.showAdd();
		//contactDAO.addContact(contact);
	}
	
	
	
	public void getDetails(Contact contact) {
		rightPanel.showDetails();
		rightPanel.detailsOfContact(contact);
	}
	
	
	
	public void updateContact(Contact contact) {
		contactDAO.updateContact(contact);
	}
	
	
	
	public void deleteContact(int id) {
		contactDAO.deleteContact(id);
	}
	
	
	
	public void setFavorite(int id, boolean favorite) {
		contactDAO.setFavorite(id, favorite);
	}
	
	
	
	
	
	
}
