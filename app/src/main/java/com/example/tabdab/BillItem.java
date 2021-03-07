package com.example.tabdab;

import android.util.Log;

import com.google.gson.Gson;

public class BillItem {
    // Instance variables
    double price;
    String name;

    // Constructor
    public BillItem() {
        this.price = 0.0;
        this.name = "";
    }

    public BillItem(double price, String name) {
        this.price = price;
        this.name = name;
    }

    // Getters
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        if (name.contains(",") || name.contains(":") || name.contains("$")) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    public String toString() {
        return name + ": " + price;
    }

    public static String toJson(BillItem billItem) {
        Gson gson = new Gson();
        return gson.toJson(billItem);
    }

    public static BillItem fromJson(String str) {
        return new Gson().fromJson(str, BillItem.class);
    }
}
