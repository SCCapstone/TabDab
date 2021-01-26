package com.example.tabdab;

import java.util.ArrayList;
import java.util.List;

public class User {
  String firstName, lastName, email, vendorID;
  boolean isVendor;
  List<Bill> pastPayments;

  public User() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.isVendor = false;
    this.vendorID = "";
    this.pastPayments = new ArrayList<>();
  }

  public User (String firstName, String lastName, String email, boolean isVendor,
               String vendorID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.isVendor = isVendor;
    this.vendorID = vendorID;
    this.pastPayments = new ArrayList<>();
  }

  // Setters
  public void setIsVendor (boolean val) {this.isVendor = val;}
  public void setFirstName (String name) {this.firstName = name;}
  public void setLastName (String name) {this.lastName = name;}
  public void setEmail (String email) {this.email = email;}
  public void setVendorID (String vendorID) {this.vendorID = vendorID;}
  public void setPastPayments (List<Bill> pastPayments) {
    this.pastPayments = pastPayments;
  }

  // Getters
  public String getFirstName () {return this.firstName;}
  public String getLastName () {return this.lastName;}
  public String getEmail () {return this.email;}
  public String getVendorID () {return this.vendorID;}
  public boolean getIsVendor () {return this.isVendor;}
  public List<Bill> getPastPayments () {return this.pastPayments;}

  public void addPastPayment (Bill bill) {
    this.pastPayments.add(bill);
  }

  public String toString () {
    return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID;
  }
}
