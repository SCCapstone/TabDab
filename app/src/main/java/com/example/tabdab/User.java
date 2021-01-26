package com.example.tabdab;

public class User {
  String firstName, lastName, email, vendorID, cardNum, expDate, CVV;
  boolean isVendor;

  public User() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.isVendor = false;
    this.vendorID = "";
    this.cardNum = "";
    this.expDate = "";
    this.CVV = "";
  }

  public User (String firstName, String lastName, String email, boolean isVendor,
               String vendorID, String cardNum, String expDate, String CVV) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.isVendor = isVendor;
    this.vendorID = vendorID;
    this.cardNum = cardNum;
    this.expDate = expDate;
    this.CVV = CVV;
  }

  // Setters
  public void setIsVendor (boolean val) {this.isVendor = val;}
  public void setFirstName (String name) {this.firstName = name;}
  public void setLastName (String name) {this.lastName = name;}
  public void setEmail (String email) {this.email = email;}
  public void setVendorID (String vendorID) {this.vendorID = vendorID;}
  public void setCardNum (String cardNum) {this.cardNum = cardNum;}
  public void setExpDate (String expDate) {this.expDate = expDate;}
  public void setCVV (String CVV) {this.CVV = CVV;}

  // Getters
  public String getFirstName () {return this.firstName;}
  public String getLastName () {return this.lastName;}
  public String getEmail () {return this.email;}
  public String getVendorID () {return this.vendorID;}
  public boolean getIsVendor () {return this.isVendor;}
  public String getCardNum () {return this.cardNum;}
  public String getExpDate () {return this.expDate;}
  public String getCVV () {return this.expDate;}

  public String toString () {
    return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID + "\n" + this.cardNum + "\n" + this.expDate + "\n" + this.CVV;
  }
}
