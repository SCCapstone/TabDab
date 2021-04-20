package com.example.tabdab;

public class PaymentTracker {
  // Instance Variables
  String vendorId;
  String userId;
  double grandTotal;
  double tip;

  // Constructors
  public PaymentTracker () {
    this.vendorId = "";
    this.userId = "";
    this.grandTotal = 0.0;
    this.tip = 0.0;
  }
  public PaymentTracker (String vendorId, String userId, double grandTotal, double tip) {
    this.vendorId = vendorId;
    this.userId = userId;
    this.grandTotal = grandTotal;
    this.tip = tip;
  }

  // Getters
  public String getVendorId () {return this.vendorId;}
  public String getUserId () {return this.userId;}
  public double getGrandTotal () {return this.grandTotal;}
  public double getTip () {return this.tip;}

  // Setters
  public void setVendorId (String vendorId) {this.vendorId = vendorId;}
  public void setUserId (String userId) {this.userId = userId;}
  public void setGrandTotal (double grandTotal) {this.grandTotal = grandTotal;}
  public void setTip (double tip) {this.tip = tip;}
}
