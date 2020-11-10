package com.example.tabdab;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    // Instance variables
    double grandTotal;
    BillItem[] itemizedBill;

    // Constructor
    public Bill () {
        this.grandTotal = 0.0;
        this.itemizedBill = new BillItem[10];  // TODO Get rid of magic number
    }

    // Getters
    public double getGrandTotal () {
        // TODO Loop through and update grand total
        return grandTotal;
    }
    public BillItem[] getItemizedBill () {
        return itemizedBill;
    }

    // Setters
    public void addItem (BillItem item, int i) {
        itemizedBill[i] = item;
    }

    public static String decode(String str) {
        str = str.substring(str.indexOf(','));  // Get rid of grand total
        return str.replaceAll(",", "\n");
    }

    public void printBill () {
        for (BillItem billItem : itemizedBill) {
            System.out.println(billItem.name);
        }
    }

    // TODO toString/encodeBill
}

