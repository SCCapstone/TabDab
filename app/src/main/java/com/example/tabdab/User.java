package com.example.tabdab;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class User {
  private String firstName, lastName, email, vendorID, cardNum, expDate, CVV;
  private boolean isVendor;
  private List<Bill> pastPayments;
  private List<Friend> friends;

  public User() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.isVendor = false;
    this.vendorID = "";
    this.cardNum = "";
    this.expDate = "";
    this.CVV = "";
    this.pastPayments = new ArrayList<>();
    this.pastPayments.add(new Bill());
    this.friends = new ArrayList<>();
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
    this.pastPayments = new ArrayList<>();
    this.friends = new ArrayList<>();

    // For firebase purposes
    BillItem item = new BillItem(1, "test");
    Bill bill = new Bill();
    bill.addBillItem(item);
    this.pastPayments.add(bill);
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
  public void setPastPayments (List<Bill> pastPayments) {
    this.pastPayments = pastPayments;
  }
  public void setFriends (List<Friend> friends) { this.friends = friends;}

  // Getters
  public String getFirstName () {return this.firstName;}
  public String getLastName () {return this.lastName;}
  public String getEmail () {return this.email;}
  public String getVendorID () {return this.vendorID;}
  public boolean getIsVendor () {return this.isVendor;}
  public String getCardNum () {return this.cardNum;}
  public String getExpDate () {return this.expDate;}
  public String getCVV () {return this.CVV;}
  public List<Bill> getPastPayments () {return this.pastPayments;}
  public List<Friend> getFriends () {return this.friends;}

  public void addPastPayment (Bill bill) {this.pastPayments.add(bill);}
  public void addFriend (Friend friend) {this.friends.add(friend);}

  public String toJson () {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static User fromJson (String str) {
    return new Gson().fromJson(str, User.class);
  }

  public String toString () {
    return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID + "\n" + this.cardNum + "\n" + this.expDate + "\n" + this.CVV;
  }
}
