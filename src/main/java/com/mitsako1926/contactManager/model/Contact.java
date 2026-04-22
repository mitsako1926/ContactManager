package com.mitsako1926.contactManager.model;

public class Contact {

   
	private int id;
    private boolean favorite;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String company;
    private String notes;
    private String imagePath;
    
    
    
    public Contact(String firstName, String lastName, String phone, String email, String company, String notes, boolean favorite, String imagePath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.notes = notes;
        this.favorite = favorite;
        this.imagePath = imagePath;
    }
    
    
    
    public Contact(int id, String firstName, String lastName, String phone, String email, String company, String notes, boolean favorite, String imagePath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.notes = notes;
        this.favorite = favorite;
        this.imagePath = imagePath;
    }
    
    
    
    public int getId() { return id; }
    
    public boolean isFavorite() { return favorite; }
    
    public String getFirstName() { return firstName; }
    
    public String getLastName() { return lastName; }
    
    public String getPhone() { return phone; }
    
    public String getEmail() { return email; }
    
    public String getCompany() { return company; }
    
    public String getNotes() { return notes; }
    
    public String getImagePath() { return imagePath; }
    

    public void setId(int id) { this.id = id; }
    
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public void setPhone(String phone) { this.phone = phone; }
    
    public void setEmail(String email) { this.email = email; }
    
    public void setCompany(String company) { this.company = company; }
    
    public void setNotes(String notes) { this.notes = notes; }
        
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    
    
    @Override
    public String toString() {
        return firstName.trim() + " " + lastName.trim();
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact c = (Contact) o;
        
        if (this.id == 0 || c.id == 0) return false;
        
        return id==c.id;
    }

    
    
    @Override
    public int hashCode() {
    	return Integer.hashCode(id);
    }
    
 
    
}
