package com.mitsako1926.contactManager.service;

import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mitsako1926.contactManager.dao.ContactDAO;
import com.mitsako1926.contactManager.gui.ContactManagerCenterPanel;
import com.mitsako1926.contactManager.gui.ContactManagerRightPanel;
import com.mitsako1926.contactManager.gui.ContactManagerSideBarPanel;
import com.mitsako1926.contactManager.model.Contact;

public final class ContactService {
	
	private String selectedImagePath;
	
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
		
	}
	
	
	
	public void addContactToDB(ArrayList<JTextField> list,String notes) {
		
		String firstName = list.get(0).getText();
	    String lastName  = list.get(1).getText();
	    String phone     = list.get(2).getText();
	    String email     = list.get(3).getText();
	    String company   = list.get(4).getText();
	    String favorite  = list.get(5).getText();

	    String isValid = inputValidation(firstName, lastName, phone, email, company, favorite);
	    
	    if(isValid!=null) {
	    	optionPaneInvalid(isValid);	
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
		
		contactDAO.addContact(contact);
				
		rightPanel.resetAddPanel();
		
		loadAllContacts();
		
		Contact contactWithId = findExistingContact(contacts, contact);

		getDetails(contactWithId);
		
		centerPanel.setSelectedContact(contactWithId);
		
		selectedImagePath = null;
	}
	
	
	
	public Contact findExistingContact(List<Contact> contacts, Contact target) {

		for (Contact c : contacts) {
	        if (c.getPhone().equals(target.getPhone()) && c.getFirstName().equals(target.getFirstName()) && c.getLastName().equals(target.getLastName())) {
	            return c;
	        }
	    }
	    return null;
	}
	
	
	
	private String inputValidation(String firstName, String lastName, String phone, String email, String company, String favorite) {

	    if (firstName == null) return "First name is null.";
	    if (lastName == null) return "Last name is null.";
	    if (phone == null) return "Phone is null.";
	    if (email == null) return "Email is null.";
	    if (company == null) return "Company is null.";
	    if (favorite == null) return "Favorite is null.";

	    firstName = firstName.trim();
	    lastName = lastName.trim();
	    phone = phone.trim();
	    email = email.trim();
	    company = company.trim();
	    favorite = favorite.trim();

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

	    return null;
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
		
		Object selected = optionPaneDelete(contact);

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
	
	
	
	public ImageIcon setUserIcon() {
	    FileDialog dialog = new FileDialog((Frame) null, "Select Image", FileDialog.LOAD);
	    dialog.setVisible(true);

	    String file = dialog.getFile();
	    String dir = dialog.getDirectory();

	    if (file != null) {
	        String fullPath = dir + file;

	        String relativePath = processImage(fullPath);

	        selectedImagePath = relativePath;

	        return loadImage(relativePath);
	    }

	    return null;
	}
	
	
	
	Path USER_IMAGES_DIR = Path.of("user-images");
	
	
	
	public String processImage(String fullPath) {
	    File selectedFile = new File(fullPath);

	    String builtIn = checkBuiltIn(selectedFile);
	    if (builtIn != null) {
	        return builtIn;
	    }

	    try {
	        Files.createDirectories(USER_IMAGES_DIR);

	        String extension = getExtension(selectedFile.getName());
	        String newName = UUID.randomUUID() + extension;

	        Path target = USER_IMAGES_DIR.resolve(newName);
	        
	        Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);

	        return "user-images/" + newName;

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	
	private String checkBuiltIn(File file) {
	    try {
	        String selected = file.getCanonicalPath();

	        String[][] builtIns = {
	            {"/images/users/woman1.png", "src/main/resources/images/users/woman1.png"},
	            {"/images/users/woman2.png", "src/main/resources/images/users/woman2.png"},
	            {"/images/users/man1.png", "src/main/resources/images/users/man1.png"},
	            {"/images/users/man2.png", "src/main/resources/images/users/man2.png"},
	            {"/images/users/man3.png", "src/main/resources/images/users/man3.png"},
	            {"/images/users/user.png", "src/main/resources/images/users/user.png"}
	        };

	        for (String[] entry : builtIns) {
	            File f = new File(entry[1]);
	            if (f.exists() && f.getCanonicalPath().equals(selected)) {
	                return entry[0];
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	
	
	private String getExtension(String name) {
	    int i = name.lastIndexOf('.');
	    return i > 0 ? name.substring(i) : "";
	}
	
	
	
	public ImageIcon loadImage(String path) {

	    if (path.startsWith("/images/")) {
	        return new ImageIcon(getClass().getResource(path));
	    }

	    if (path.startsWith("user-images/")) {
	    	String fileName = path.substring("user-images/".length());
	        Path p = USER_IMAGES_DIR.resolve(fileName);
	        return new ImageIcon(p.toString());
	    }

	    return null;
	}
	
	
	
	private Object optionPaneDelete(Contact contact) {
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
	
	
	
	private void optionPaneInvalid(String text) {
		
		String message = "<html><body style='font-size:11px;'>"
		        		+ text + "</b>"+ "</body></html>";

		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/icons/warning.png"));
	    Image img = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		
		JOptionPane optionPane = new JOptionPane(
		    message,
		    JOptionPane.PLAIN_MESSAGE,
		    JOptionPane.YES_NO_OPTION,
		    null,
		    new Object[]{"OK"}
		);
		
		JDialog dialog = optionPane.createDialog("Invalid Input");
		
		dialog.setIconImage(img);
		
		removeFocus(optionPane);

		dialog.setVisible(true);
		
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
