package com.example.tabdab;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vendor {
    String vendorId, name;
    List<BillItem> menu;
    List<Bill> previousPayments;
    Map<String, List<BillItem>> dailyTotals;

    public Vendor() {
        this.vendorId = "";
        this.name = "";
        this.menu = new ArrayList<>();
        this.previousPayments = new ArrayList<>();
        this.dailyTotals = new HashMap<>();
    }

    public Vendor(String name) {
        this.vendorId = "";
        this.name = name;
        this.menu = new ArrayList<>();
        this.previousPayments = new ArrayList<>();
        this.dailyTotals = new HashMap<>();
    }

    public Vendor(String vendorId, String name) {
        this.vendorId = vendorId;
        this.name = name;
        this.menu = new ArrayList<>();
        this.previousPayments = new ArrayList<>();
        this.dailyTotals = new HashMap<>();
    }

    public Vendor(String vendorId, String name, List<BillItem> menu, List<Bill> previousPayments,
                  HashMap<String, List<BillItem>> dailyTotals) {
        this.vendorId = vendorId;
        this.name = name;
        this.menu = menu;
        this.previousPayments = previousPayments;
        this.dailyTotals = dailyTotals;
    }

    // Getters
    public String getVendorId() {
        return this.vendorId;
    }
    public String getName() {
        return this.name;
    }
    public List<BillItem> getMenu() {
        return this.menu;
    }
    public List<Bill> getPreviousPayments () {return this.previousPayments;}
    public Map<String, List<BillItem>> getDailyTotals () {return this.dailyTotals;}

    // Setters
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMenu(List<BillItem> menu) {
        this.menu = menu;
    }
    public void setPreviousPayments (List<Bill> previousPayments) {this.previousPayments = previousPayments;}
    public void setDailyTotals (Map<String, List<BillItem>> dailyTotals) {this.dailyTotals = dailyTotals;}

    public void addPreviousPayment (Bill bill) {this.previousPayments.add(bill);}
    public void addDailyTotalItems (Bill bill) {
        String mapString = bill.getDate().replace('/', ' ');
        /* If the map doesn't contain the key then add the bill items, if it does then merge the
         old list of items and the current bill */
        if (this.dailyTotals.containsKey(mapString)) {
            List<BillItem> daily = this.dailyTotals.get(mapString);
            daily.addAll(bill.getItemizedBill());
            this.dailyTotals.put(mapString, daily);
        } else {
            this.dailyTotals.put(mapString, bill.getItemizedBill());
        }
    }

    public String menuToString() {
        String str = "";
        for (int i = 0; i < this.menu.size(); i++) {
            str = str + this.menu.get(i).getName() + ": " + this.menu.get(i).getPrice() + "\n";
        }
        return str;
    }

    public String toString () {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
