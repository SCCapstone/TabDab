package com.example.tabdab;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bill {
    // Instance variables
    double grandTotal;
    double tip;
    List<BillItem> itemizedBill;

    // Constructors
    public Bill () {
        this.itemizedBill = new ArrayList<>();
        this.grandTotal = 0.0;
        this.tip = 0.0;
    }
    public Bill (List<BillItem> itemizedBill, double grandTotal, double tip) {
        this.itemizedBill = itemizedBill;
        this.grandTotal = grandTotal;
        this.tip = tip;
    }

    // Getters
    public double getGrandTotal () {
        return this.grandTotal;
    }
    public double getTip () {
        return this.tip;
    }
    public List<BillItem> getItemizedBill () {return this.itemizedBill;}

    /**
     * Set the grand total of the bill
     * @param grandTotal
     */
    public void setGrandTotal (double grandTotal) {
        this.grandTotal = grandTotal;
    }
    public void setGrandTotal () {
        for (BillItem item : this.itemizedBill) {
            this.grandTotal += item.getPrice();
        }
    }
    public void setItemizedBill (List<BillItem> itemizedBill) {
        this.itemizedBill = itemizedBill;
    }

    /**
     * Set the bill's tip
     * @param tip Tip you want to add
     */
    public void setTip (double tip) {
        this.tip = tip;
    }

    public void addBillItem (BillItem item) {
        this.itemizedBill.add(item);
        setGrandTotal();
    }


    public String toString () {
        String ret = "";
        for (int i = 0; i < this.itemizedBill.size(); i++) {
            ret += this.itemizedBill.get(i).getName() + ": $" + this.itemizedBill.get(i).getPrice() + "\n";
        }
        ret += "Tip: " + this.getTip();
        return ret;
    }

    public String toJson () {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Bill fromJson (String str) {
        return new Gson().fromJson(str, Bill.class);
    }


    /**
     * Prints the grand total and itemized bill
     */
    public void printBill () {
        System.out.println("Grand Total: " + grandTotal);
        for (int i = 0; i < itemizedBill.size(); i++) {
            System.out.println(itemizedBill.get(i).getName() + ", " + itemizedBill.get(i).getPrice()+ "\n");
        }
        System.out.println("Tip: " + tip);
    }

    /**
     * Checks that the QR code string is of the proper format.
     * @param str
     * @return
     */
    public static boolean isQRCodeStringFormat (String str) {
        if (str.contains(",") || str.contains("$") || str.contains(":")) {
            Log.d("Bill.java", "QR code string contains one of the following: ',', '$', ':'");
            return false;
        }
        return true;
    }
}

