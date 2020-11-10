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
    public static String getGrandTotal (String str) {
        double total = 0.0;
        String[] items = str.split(",");
        for (int i = 0; i < items.length; i++) {
            // TODO Clean up the decoding process
            total += Double.parseDouble(items[i].substring(items[i].indexOf('$')+1));
        }
        return String.valueOf(total);
    }

    public static String decode(String str) {
        return str.replaceAll(",", "\n");
    }

    public void printBill () {
        for (BillItem billItem : itemizedBill) {
            System.out.println(billItem.name);
        }
    }

    // TODO toString/encodeBill
}

