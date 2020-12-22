package com.example.tabdab;

public class Vendor {
  String vendorId, name;
  Menu menu;

  public Vendor () {
    this.vendorId = "";
    this.name = "";
    this.menu = new Menu();
  }

  public Vendor (String name) {
    this.vendorId = "";
    this.name = name;
    this.menu = new Menu();
  }

  public Vendor (String vendorId, String name) {
    this.vendorId = vendorId;
    this.name = name;
    this.menu = new Menu();
  }

  // Getters
  public String getVendorId () {return this.vendorId;}
  public String getName () {return this.name;}
  public Menu getMenu () {return this.menu;}

  // Setters
  public void setVendorId (String vendorId) {this.vendorId = vendorId;}
  public void setName (String name) {this.name = name;}
  public void setMenu (Menu menu) {this.menu = menu;}


}
