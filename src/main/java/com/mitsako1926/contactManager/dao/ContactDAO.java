package com.mitsako1926.contactManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mitsako1926.contactManager.db.DatabaseConnection;
import com.mitsako1926.contactManager.model.Contact;


public final class ContactDAO {

	
	public List<Contact> getAllContacts() {
		
		List<Contact> list = new ArrayList<Contact>();
		
		String sql = "Select * from contacts";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	 ResultSet rs = ps.executeQuery()) {

	        while(rs.next()) list.add(mapResultSetToContact(rs));
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving all contacts");
	    }
	    	
        return list;

	}
	
	
	
	public void addContact(Contact contact) {
		
		String sql = "INSERT INTO contacts (first_name, last_name, phone, email, company, notes, favorite) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, contact.getFirstName());
	        ps.setString(2, contact.getLastName());
	        ps.setString(3, contact.getPhone());
	        ps.setString(4, contact.getEmail());
	        ps.setString(5, contact.getCompany());
	        ps.setString(6, contact.getNotes());
	        ps.setBoolean(7, contact.isFavorite());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error adding new contact");
	    }
	    
	}

    
	
	public void deleteContact(int id) {
    	
    	String sql = "DELETE FROM contacts WHERE id = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ps.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error deleting a contact");
	    }
	    
    }
	
	

    public Optional<Contact> getContactById(int id) {
    			
		String sql = "Select * from contacts Where id = ?";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	    	ps.setInt(1, id);
	    	
	    	try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return Optional.of(mapResultSetToContact(rs));
	            }
	        }	    	
	        
	    } catch (SQLException e) {
	        System.out.println("Error retrieving contact");
	    }
	    	
        return Optional.empty();
        
    }
    
    
    
    public List<Contact> getFavoriteContacts() {
		
    	List<Contact> list = new ArrayList<Contact>();
    	
		String sql = "Select * from contacts Where favorite = 1";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	    	PreparedStatement ps = conn.prepareStatement(sql);
	    	ResultSet rs = ps.executeQuery()) {
	    	
	    	while(rs.next()) list.add(mapResultSetToContact(rs));
	        
	    } catch (SQLException e) {
	        System.out.println("Error retrieving favorite contacts");
	    }
	    	
        return list;
        
    }

    
    
    public void setFavorite(int id, boolean favorite) {
    	
    	String sql = "Update contacts set favorite = ? WHERE id = ?";
    	
    	try (Connection conn = DatabaseConnection.getConnection();
   	         PreparedStatement ps = conn.prepareStatement(sql)) {

   	        ps.setInt(2, id);
   	        ps.setBoolean(1, favorite);
   	        
   	        ps.executeUpdate();

   	    } catch (SQLException e) {
   	        System.out.println("Error setting a contact as favorite");
   	    }
    	
    }
    
    
    
    public void updateContact(Contact contact) {
    	
    }

    

    public List<Contact> searchContacts(String keyword) {
    	return new ArrayList<>();
    }
    
    
    
    
    private Contact mapResultSetToContact(ResultSet rs) throws SQLException {
        return new Contact(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getString("company"),
            rs.getString("notes"),
            rs.getBoolean("favorite")
        );
    }
    
    
}
