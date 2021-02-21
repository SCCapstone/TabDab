package com.example.tabdab;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
  String firstName, lastName, email, vendorID, cardNum, expDate, CVV;
  boolean isVendor;
  List<Bill> pastPayments;

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
  public void setEmail (String email) {
      this.email = email;
  }
  public void setVendorID (String vendorID) {this.vendorID = vendorID;}
  public void setCardNum (String cardNum) {
      this.cardNum = cardNum;
  }
  public void setExpDate (String expDate) {
      this.expDate = expDate;
  }
  public void setCVV (String CVV) {
      this.CVV = CVV;
  }
  public void setPastPayments (List<Bill> pastPayments) {this.pastPayments = pastPayments;}

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

  public void addPastPayment (Bill bill) {this.pastPayments.add(bill);
  }

  public String toString () {
    return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID + "\n" + this.cardNum + "\n" + this.expDate + "\n" + this.CVV;
  }
}
