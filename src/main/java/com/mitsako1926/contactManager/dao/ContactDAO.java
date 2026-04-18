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
		
		String sql = "INSERT INTO contacts (first_name, last_name, phone, email, company, notes, favorite, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, contact.getFirstName());
	        ps.setString(2, contact.getLastName());
	        ps.setString(3, contact.getPhone());
	        ps.setString(4, contact.getEmail());
	        ps.setString(5, contact.getCompany());
	        ps.setString(6, contact.getNotes());
	        ps.setBoolean(7, contact.isFavorite());
	        ps.setString(8, contact.getImagePath());

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
    	
        String sql = "Update contacts set first_name = ?, last_name = ?, phone = ?, email = ?, company = ?, notes = ?, favorite = ? , image_path = ? WHERE id = ?";
    	
    	try (Connection conn = DatabaseConnection.getConnection();
   	         PreparedStatement ps = conn.prepareStatement(sql)) {

    		ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getEmail());
            ps.setString(5, contact.getCompany());
            ps.setString(6, contact.getNotes());
            ps.setBoolean(7, contact.isFavorite());
            ps.setString(8, contact.getImagePath());
            ps.setInt(9, contact.getId());
   	        
   	        ps.executeUpdate();

   	    } catch (SQLException e) {
   	    	System.out.println("Error updating contact");
   	    }
    
    }

    

    public List<Contact> searchContacts(String keyword) {
    	
    	if (keyword == null || keyword.trim().isEmpty()) {
    	    return getAllContacts();
    	}
    	
    	List<Contact> list = new ArrayList<Contact>();
		
    	String sql = "Select * from contacts where " +
                "lower(first_name) like ? or " +
                "lower(last_name) like ? or " +
                "phone like ? or " +
                "lower(email) like ? or " +
                "lower(company) like ?";
    	
    	try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	    	
	    	String searchPattern = "%" + keyword.trim().toLowerCase() + "%";
	    	
	    	ps.setString(1, searchPattern);
	        ps.setString(2, searchPattern);
	        ps.setString(3, searchPattern);
	        ps.setString(4, searchPattern);
	        ps.setString(5, searchPattern);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(mapResultSetToContact(rs));
	            }
	        }
	    		                
	    } catch (SQLException e) {
	    	System.out.println("Error searching contacts");	    
	    }
	    	
        return list;
    }
    
    
    
    public List<Contact> getAllContactsOrderByFirstName(){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
		String sql = "Select * from contacts order by first_name ASC";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	 ResultSet rs = ps.executeQuery()) {

	        while(rs.next()) list.add(mapResultSetToContact(rs));
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving all contacts (first name order)");
	    }
	    	
        return list;
        
    }
    
    
    
    public List<Contact> getAllContactsOrderByLastName(){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
		String sql = "Select * from contacts order by last_name ASC";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	 ResultSet rs = ps.executeQuery()) {

	        while(rs.next()) list.add(mapResultSetToContact(rs));
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving all contacts (last name order)");
	    }
	    	
        return list;
        
    }
    
    
    
    public List<Contact> getAllContactsOrderByFavoriteFirst(){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
		String sql = "Select * from contacts order by favorite desc,last_name ASC";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	 ResultSet rs = ps.executeQuery()) {

	        while(rs.next()) list.add(mapResultSetToContact(rs));
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving all contacts (favorite first)");
	    }
	    	
        return list;
        
    }
    
    
    
    public boolean existsByPhone(String phone) {
    			
		String sql = "Select 1 from contacts where phone = ?";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	    	ps.setString(1, phone.trim());
	    	
	    	try(ResultSet rs = ps.executeQuery()){
	    		return rs.next();
	    	}
	        
	                
	    } catch (SQLException e) {
	        System.out.println("Error trying to see if contact exists by phone");
	    }
	    	
        return false;
        
    }
    
    
    
    public boolean existsByEmail(String email) {
    			
		String sql = "Select 1 from contacts where email = ?";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	    	ps.setString(1, email.trim());
	    	
	    	try(ResultSet rs = ps.executeQuery()){
	    		return rs.next();
	    	}
	        
	                
	    } catch (SQLException e) {
	        System.out.println("Error trying to see if contact exists by email");
	    }
	    	
        return false;
    	
    }
    
    
    
    public int countContacts() {
    			
		String sql = "Select count(*) from contacts";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	ResultSet rs = ps.executeQuery()) {

	    	if(rs.next()) return rs.getInt(1);
	        
	                
	    } catch (SQLException e) {
	    	System.out.println("Error counting contacts");
	    }
	    	
        return 0;
        
    }
    
    
    
    public int countFavoriteContacts() {
    			
		String sql = "Select count(*) from contacts where favorite = 1";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	    	ResultSet rs = ps.executeQuery()) {

	    	if(rs.next()) return rs.getInt(1);
	        
	                
	    } catch (SQLException e) {
	    	System.out.println("Error counting favorite contacts");
	    }
	    	
        return 0;
        
    }
    
    
    
    public List<Contact> getContactsByCompany(String company){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
    	String sql = "Select * from contacts where lower(company) like ?";
		
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	    	
	    	ps.setString(1, "%" + company.trim().toLowerCase() + "%");
	    	
	    	try(ResultSet rs = ps.executeQuery()){
	    		while(rs.next()) list.add(mapResultSetToContact(rs));
	    	}
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving contacts by company");
	    }
	    	
        return list;	
    }
    
    
    
    public List<Contact> getContactsByEmailDomain(String domain){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
    	String sql = "Select * from contacts where lower(email) like ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%@" + domain.trim().toLowerCase());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToContact(rs));
                }
            }
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving contacts by email domain");
	    }
	    	
        return list;
    	
    }
    
    
    
    public List<Contact> getContactsByPhonePrefix(String prefix){
    	
    	List<Contact> list = new ArrayList<Contact>();
		
    	String sql = "SELECT * FROM contacts WHERE phone LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prefix.trim() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToContact(rs));
                }
            }
	                
	    } catch (SQLException e) {
	        System.out.println("Error retrieving contacts by phone prefix");
	    }
	    	
        return list;
    	
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
            rs.getBoolean("favorite"),
            rs.getString("image_path")
        );
    }



	
    
    
}
