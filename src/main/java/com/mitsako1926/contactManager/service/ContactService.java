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
		Contact contact = new Contact(
				list.get(0).getText(),
				list.get(1).getText(),
				list.get(2).getText(),
				list.get(3).getText(),
				list.get(4).getText(),
				notes,
				Boolean.valueOf(list.get(5).getText()),
				selectedImagePath
				
		);
		contactDAO.addContact(contact);
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
