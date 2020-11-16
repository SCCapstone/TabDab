package com.example.tabdab;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    // Instance variables
    double grandTotal;
    double tip;
    BillItem[] itemizedBill;

    // Constructors
    public Bill () {
        this.itemizedBill = new BillItem[20];  // TODO fix magic number
        this.grandTotal = 0.0;
        this.tip = 0.0;
    }
    public Bill (String str) {
        this.itemizedBill = setItemizedBill(str);
        this.grandTotal = setGrandTotal(str);
        this.tip = 0.0;
    }

    // Getters
    public double getGrandTotal () {
        return this.grandTotal;
    }
    public double getTip () {
        return this.tip;
    }


    /**
     * Set the grand total of the bill based off the QR code received
     * @param str Encoded QR code string
     */
    public double setGrandTotal (String str) {
        double total = 0.0;
        String[] items = str.split(",");

        for (int i = 0; i < items.length; i++) {
            // Split remove all characters before '$' then add the price to the grant total
            total += Double.parseDouble(items[i].substring(items[i].indexOf('$')+1));
        }

        total += tip;
        return total;
    }
    public void setGrandTotal (double grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * Set the bill's tip
     * @param tip Tip you want to add
     */
    public void setTip (double tip) {
        this.tip = tip;
    }

    /**
     * Decodes the QR string into an itemized bill
     * @param str string to decode from QR content
     */
    public BillItem[] setItemizedBill (String str) {
        String[] itemized = str.split(",");  // Splits encoded string into separate items
        BillItem[] ret = new BillItem[itemized.length];

        // Get each item and price from the separate items
        for (int i = 0; i < itemized.length; i++) {
            String[] components = itemized[i].split(":");

            BillItem item = new BillItem ();
            item.setName(components[0]);
            item.setPrice(Double.parseDouble(components[1].substring(components[1].indexOf('$')+1)));
            ret[i] = item;
        }
        return ret;
    }

    /**
     * Makes the itemized bill a string with tip included (mostly for BillView display)
     * @return the itemized bill as a string
     */
    public String toString () {
        String ret = "";
        for (int i = 0; i < itemizedBill.length; i++) {
            ret += itemizedBill[i].name + ": $" + itemizedBill[i].price + "\n";
        }
        ret += "Tip: " + tip;
        return ret;
    }

    /**
     * Prints the grand total and itemized bill
     */
    public void printBill () {
        System.out.println("Grand Total: " + grandTotal);
        for (int i = 0; i < itemizedBill.length; i++) {
            System.out.println(itemizedBill[i].name + ", " + itemizedBill[i].price + "\n");
        }
        System.out.println("Tip: " + tip);
    }

    // TODO Check that the string read from the QR code is of the TabDab format.
}

