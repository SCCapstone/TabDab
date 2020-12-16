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

  public User (String firstName, String lastName, String email, boolean isVendor,
               String vendorID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.isVendor = isVendor;
    this.vendorID = vendorID;
  }

  // Setters
  public void setVendor (boolean val) {this.isVendor = val;}

  // Getters
  public String getFirstName () {return this.firstName;}
  public String getLastName () {return this.lastName;}
  public String getEmail () {return this.email;}
  public String getVendorID () {return this.vendorID;}
  public boolean getIsVendor () {return this.isVendor;}
}
