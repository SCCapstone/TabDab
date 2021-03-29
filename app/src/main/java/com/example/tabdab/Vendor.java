package com.example.tabdab;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vendor {
    String vendorId, name;
    List<BillItem> menu;

    public Vendor() {
        this.vendorId = "";
        this.name = "";
        this.menu = new ArrayList<>();
    }

    public Vendor(String name) {
        this.vendorId = "";
        this.name = name;
        this.menu = new ArrayList<>();
    }

    public Vendor(String vendorId, String name) {
        this.vendorId = vendorId;
        this.name = name;
        this.menu = new ArrayList<>();
    }

    public Vendor(String vendorId, String name, List<BillItem> menu) {
        this.vendorId = vendorId;
        this.name = name;
        this.menu = menu;
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
    public static Vendor fromJson (String str) {
        return new Gson().fromJson(str, Vendor.class);
    }
}
