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
    
    
    
    public Contact(String firstName, String lastName, String phone, String email, String company, String notes,boolean favorite) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.notes = notes;
    }
    
    
    
    public Contact(int id, String firstName, String lastName, String phone, String email, String company, String notes,boolean favorite) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.notes = notes;
    }
    
    
    
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getNotes() {
        return notes;
    }
    
    public boolean isFavorite() {
        return favorite;
    }
    
    
}
