package com.example.tabdab;

public class User {
    String firstName, lastName, email;
    boolean isVendor;

    public User() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.isVendor = false;
    }

    public User (String firstName, String lastName, String email, boolean isVendor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVendor = isVendor;
    }

    public void setVendor (boolean val) {this.isVendor = val;}

    public String getFirstName () {return this.firstName;}
    public String getLastName () {return this.lastName;}
    public String getEmail () {return this.email;}
    public boolean getIsVendor () {return this.isVendor;}
}
