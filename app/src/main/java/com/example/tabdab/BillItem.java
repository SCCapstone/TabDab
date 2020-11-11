package com.example.tabdab;

public class BillItem {
    // Instance variables
    double price;
    String name;

    // Constructor
    public BillItem () {
        this.price = 0.0;
        this.name = "";
    }
    public BillItem (double price, String name) {
        this.price = price;
        this.name = name;
    }

    // Getters
    public double getPrice () {
        return price;
    }
    public String getName () {
        return name;
    }

    // Setters
    public void setPrice (double price) {
        this.price = price;
    }
    public void setName (String name) {
        this.name = name;
    }
}
