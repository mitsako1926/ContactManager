package com.mitsako1926.contactManager.service;


import java.awt.FileDialog;
import java.awt.Frame;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.mitsako1926.contactManager.dao.ContactDAO;
import com.mitsako1926.contactManager.gui.ContactManagerCenterPanel;
import com.mitsako1926.contactManager.gui.ContactManagerRightPanel;
import com.mitsako1926.contactManager.gui.ContactManagerSideBarPanel;
import com.mitsako1926.contactManager.gui.DialogUtils;
import com.mitsako1926.contactManager.model.Contact;

public final class ContactService {
	
	private String selectedImagePath;
	
	private final ContactDAO contactDAO = new ContactDAO();
			
	private  ContactManagerSideBarPanel sideBarPanel;
	
	private ContactManagerCenterPanel centerPanel;
		
	private ContactManagerRightPanel rightPanel;
	
	private List<Contact> contacts = contactDAO.getAllContacts();
	
	
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
	
	
	
	public enum ViewMode{
		ALL, FAVORITES, SEARCH
	}
	
	
	
	public void loadAllContacts(){
		contacts = contactDAO.getAllContacts();
		centerPanel.callRefresh(ViewMode.ALL);
		rightPanel.showEmpty();
		rightPanel.detailsOfContact(null);
		rightPanel.resetAddPanel();
		resetFilters();
		
	}
	
	
	
	public void loadFavoriteContacts(){
		contacts = contactDAO.getFavoriteContacts();
		centerPanel.callRefresh(ViewMode.FAVORITES);	
		rightPanel.showEmpty();
		rightPanel.detailsOfContact(null);
		rightPanel.resetAddPanel();
		resetFilters();
		
	}
	

	
	public void setFavorite() {
		Contact contactToFavorite = rightPanel.getContact();
		
		if(contactToFavorite==null) return;
		
		if(!contactToFavorite.equals(centerPanel.getSelectedContact())) {
			return;
		}
				
		contactDAO.setFavorite(contactToFavorite.getId(), !contactToFavorite.isFavorite());
		
		contactToFavorite.setFavorite(!contactToFavorite.isFavorite());
		
		getDetails(contactToFavorite);
		
	}
	
	
	
	public ImageIcon setUserIcon() {
	    FileDialog dialog = new FileDialog((Frame) null, "Select Image", FileDialog.LOAD);
	    dialog.setVisible(true);

	    String file = dialog.getFile();
	    String dir = dialog.getDirectory();

	    if (file != null) {
	        String fullPath = dir + file;

	        String relativePath = ImageUtils.processImage(fullPath);

	        selectedImagePath = relativePath;

	        return ImageUtils.loadImage(relativePath);
	    }

	    return null;
	    
	}
	
	
	
	public void deleteContact() {		
		Contact contactToDelete = rightPanel.getContact();
		
		if(contactToDelete==null) return;
		
		if(!contactToDelete.equals(centerPanel.getSelectedContact())) {
			return;
		}
				
		Object selected = DialogUtils.optionPaneDelete(contactToDelete);

		if ("Delete".equals(selected)) {
		    contactDAO.deleteContact(contactToDelete.getId());
		    
		    loadAllContacts();
		    
		    sideBarPanel.selectButton(sideBarPanel.getAllContactsButton());
		    
		    rightPanel.showEmpty();
		    rightPanel.detailsOfContact(null);
		}
	    
	}
	
	
	
	public void searchContacts(String keyword){
		if(keyword.isBlank()&&sideBarPanel.getSelectedButton().getText().equals("All Contacts")) {
			contacts = contactDAO.getAllContacts();
			centerPanel.callRefresh(ViewMode.ALL);
			return;
		}else if(keyword.isBlank()&&sideBarPanel.getSelectedButton().getText().equals("Favorites")) {
			contacts = contactDAO.getFavoriteContacts();
			centerPanel.callRefresh(ViewMode.FAVORITES);
			return;
		}else if(keyword.isBlank()&&sideBarPanel.getPreviousButton().getText().equals("All Contacts")) {
			contacts = contactDAO.getAllContacts();
			centerPanel.callRefresh(ViewMode.ALL);
			return;
		}else if(keyword.isBlank()&&sideBarPanel.getPreviousButton().getText().equals("Favorites")){
			contacts = contactDAO.getFavoriteContacts();
			centerPanel.callRefresh(ViewMode.FAVORITES);
			return;
		}
		contacts = contactDAO.searchContacts(keyword);
		centerPanel.callRefresh(ViewMode.SEARCH);
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
		sideBarPanel.selectButton(sideBarPanel.getAllContactsButton());
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
	
	
	
	public void addOrUpdateContactToDB(List<JTextField> list, String notes, String addOrUpdate) {
		
		String firstName = list.get(0).getText().trim();
	    String lastName  = list.get(1).getText().trim();
	    String phone     = list.get(2).getText().trim();
	    String email     = list.get(3).getText().trim();
	    String company   = list.get(4).getText().trim();
	    String favorite  = list.get(5).getText().trim();

	    String isValid = inputValidation(firstName, lastName, phone, email, company, favorite, addOrUpdate);
	    
	    if(isValid!=null) {
	    	DialogUtils.optionPaneInvalid(isValid);	
	    	return;
	    }
	    
	    Contact contact = new Contact(
	            firstName,
	            lastName,
	            phone,
	            email,
	            company,
	            notes,
	            Boolean.parseBoolean(favorite),
	            selectedImagePath
	    );
		
	    
	    if(addOrUpdate.contains("Add")) {
	    	contactDAO.addContact(contact);
	    	
	    	rightPanel.resetAddPanel();
			
	    	loadAllContacts();
			
			Contact contactWithId = findExistingContact(contacts, contact);

			getDetails(contactWithId);
			
			centerPanel.setSelectedContact(contactWithId);
						
			selectedImagePath = null;
			
	    }else if(addOrUpdate.contains("Update")){
	    	Contact contactWithId = rightPanel.getContact();

	    	contact.setId(contactWithId.getId());
	    	
	    	if(selectedImagePath == null) {
	    		contact.setImagePath(contactWithId.getImagePath());
	    	}
	    	
	    	contactDAO.updateContact(contact);
	    	
	    	rightPanel.resetAddPanel();
			
			loadAllContacts();
			
			contact = findExistingContactWithId(contacts,contact);
		    
			centerPanel.setSelectedContact(contact);
			
			getDetails(contact);
						
			selectedImagePath = null;
			
	    }
	    sideBarPanel.selectButton(sideBarPanel.getAllContactsButton());

	}
	
	
	
	public void addContact() {
		rightPanel.detailsOfContact(null);
		
		if(centerPanel.getList().getSelectedValue()!=null) {
			centerPanel.getList().clearSelection();
		}
		rightPanel.resetAddPanel();
		rightPanel.showAdd();
		
	}
	
	
	
	public void updateContact() {
		Contact contactToUpdate = rightPanel.getContact();
		
		if(contactToUpdate==null) return;
		
		if(!contactToUpdate.equals(centerPanel.getSelectedContact())) {
			return;
		}
				
		rightPanel.setAddToUpdatePanel(contactToUpdate);
		rightPanel.showAdd();
		
	}
	
	
	
	public void resetFilters() {
		centerPanel.getComboBox().setSelectedIndex(-1);
	}
	
	
	
	public Contact findExistingContactWithId(List<Contact> contacts, Contact target) {
		for (Contact c : contacts) { 
			if (c.equals(target)) { 
				return c; 
			} 
		}
		return null;
	}
	
	
	
	public Contact findExistingContact(List<Contact> contacts, Contact target) {

		for (Contact c : contacts) {
	        if (c.getPhone().equals(target.getPhone()) && c.getFirstName().equals(target.getFirstName()) && c.getLastName().equals(target.getLastName())) {
	            return c;
	        }
	    }
	    return null;
	    
	}
	
	
	
	private String inputValidation(String firstName, String lastName, String phone, String email, String company, String favorite, String addOrUpdate) {

	    if (firstName.isEmpty()) return "First name is required.";
	    if (lastName.isEmpty()) return "Last name is required.";
	    if (phone.isEmpty()) return "Phone is required.";

	    if (firstName.length() > 50) return "First name cannot exceed 50 characters.";
	    if (lastName.length() > 50) return "Last name cannot exceed 50 characters.";
	    if (phone.length() > 20) return "Phone cannot exceed 20 characters.";
	    if (email.length() > 100) return "Email cannot exceed 100 characters.";
	    if (company.length() > 100) return "Company cannot exceed 100 characters.";

	    if (!firstName.matches("[\\p{L} .'-]+")) {
	        return "First name contains invalid characters.";
	    }

	    if (!lastName.matches("[\\p{L} .'-]+")) {
	        return "Last name contains invalid characters.";
	    }

	    if (!phone.matches("[0-9+()\\-\\s]+")) {
	        return "Phone contains invalid characters.";
	    }

	    String digitsOnly = phone.replaceAll("\\D", "");
	    if (digitsOnly.length() < 7 || digitsOnly.length() > 15) {
	        return "Phone must contain between 7 and 15 digits.";
	    }

	    if (!email.isEmpty()) {
	        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
	            return "Invalid email format.";
	        }
	    }

	    if (!company.isEmpty()) {
	        if (!company.matches("[\\p{L}0-9 .,&'\\-()]+")) {
	            return "Company contains invalid characters.";
	        }
	    }
	    
	    if(addOrUpdate.equals("Update")) {
	    	Contact contact = rightPanel.getContact();
		    
		    if(!contact.getEmail().trim().equals(email)) {
		    	if(contactDAO.existsByEmail(email) && !email.isBlank()) {
			    	return "This email already exists";
			    }
		    }
		    
		    if(!contact.getPhone().trim().equals(phone)) {
		    	if(contactDAO.existsByPhone(phone)) {
			    	return "This phone number already exists";
			    }
		    }
		    
	    }else if(addOrUpdate.equals("Add")){
	    	
	    	if(contactDAO.existsByEmail(email) && !email.isBlank()) {
		    	return "This email already exists";
		    }
	    	
	    	if(contactDAO.existsByPhone(phone)) {
		    	return "This phone number already exists";
		    }
	    }
	    
	    
	    return null;
	    
	}
	
	

	
	
	
}

