package com.example.tabdab;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DailyTotals {
  // Instance variables
  private String vendorId;
  private List<Bill> previousPayments;
  private HashMap<String, List<BillItem>> totals;

  // Constructors
  public DailyTotals () {
    this.vendorId = "";
    this.previousPayments = new ArrayList<>();
    this.totals = new HashMap<>();
  }
  public DailyTotals (String vendorId, ArrayList<Bill> previousPayments, HashMap<String, List<BillItem>> totals) {
    this.vendorId = vendorId;
    this.previousPayments = previousPayments;
    this.totals = totals;
  }

  // Getters
  public String getVendorId () {return this.vendorId;}
  public List<Bill> getPreviousPayments () {return this.previousPayments;}
  public HashMap<String, List<BillItem>> getTotals () {return this.totals;}

  // Setters
  public void setVendorId (String vendorId) {this.vendorId = vendorId;}
  public void setPreviousPayments (List<Bill> previousPayments) {this.previousPayments = previousPayments;}
  public void setTotals (HashMap<String, List<BillItem>> totals) {this.totals = totals;}

  public void addPreviousPayment (Bill bill) {this.previousPayments.add(bill);}

  public void addDailyTotalItems (Bill bill) {
    String date = bill.getDate().replace('/', ' ');

    // If the date is not present add the bill items to the hashtable
    if (this.totals.get(date) == null) {
      this.totals.put(date, bill.getItemizedBill());
    } else {  // If the date is already present add the current bill items to the existing list
      List<BillItem> newTotals = this.totals.get(date);
      newTotals.addAll(bill.getItemizedBill());
      this.totals.put(date, newTotals);
    }
  }

  public HashMap<String, Integer> totalsListToTotals (String date) {
    HashMap<String, Integer> count = new HashMap<>();
    List<BillItem> day = this.totals.get(date);

    // Get the count of each menu item in the days daily totals
    for (int i = 0; i < day.size(); i++) {
      if (!count.containsKey(day.get(i).getName())) {
        count.put(day.get(i).getName(), 1);
      } else {
        Integer temp = count.get(day.get(i).getName());
        count.put(day.get(i).getName(), temp + 1);
      }
    }

    return count;
  }

  public String totalsListToTotalsStr (String date) {
    HashMap<String, Integer> count = new HashMap<>();
    List<BillItem> day = this.totals.get(date);
    Double grandTotal = 0.0;

    // If the date is empty return a string letting the user know that there are no entries
    if (day == null) return "No entries.";

    // Get the count of each menu item in the days daily totals and add the prices up
    for (int i = 0; i < day.size(); i++) {
      if (!count.containsKey(day.get(i).getName())) {
        count.put(day.get(i).getName(), 1);
        grandTotal += day.get(i).getPrice();
      } else {
        Integer temp = count.get(day.get(i).getName());
        count.put(day.get(i).getName(), temp + 1);
        grandTotal += day.get(i).getPrice();
      }
    }

    // Convert the counts to a string
    String ret = "";
    for (Map.Entry<String, Integer> e : count.entrySet()) {
      ret += e.getKey() + "\tx" + e.getValue() + "\n";
    }
    ret += "Total amount: $" + String.format("%.2f", grandTotal);
    return ret;
  }

  public static String countToString (HashMap<String, Integer> count) {
    String ret = "";
    for (Map.Entry<String, Integer> e : count.entrySet()) {
      ret += e.getKey() + "\tx" + e.getValue() + "\n";
    }
    return ret;
  }
}
