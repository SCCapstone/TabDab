package com.example.tabdab;

public class User {
    String firstName, lastName, email, vendorID;
    boolean isVendor;

    public User() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.isVendor = false;
        this.vendorID = "";
    }

    public User(String firstName, String lastName, String email, boolean isVendor,
                String vendorID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVendor = isVendor;
        this.vendorID = vendorID;
    }

    // Setters
    public void setIsVendor(boolean val) {
        this.isVendor = val;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    // Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getVendorID() {
        return this.vendorID;
    }

    public boolean getIsVendor() {
        return this.isVendor;
    }

    public String toString() {
        return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID;
    }
}
