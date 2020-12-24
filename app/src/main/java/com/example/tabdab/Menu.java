package com.example.tabdab;

import android.view.MenuItem;

import java.util.ArrayList;

public class Menu {
    ArrayList<BillItem> menu;

    public Menu () {
        this.menu = new ArrayList<>();
    }

    public void addItem (BillItem item) {
        this.menu.add(item);
    }

    public void printMenu () {
        for (BillItem i : this.menu) {
            System.out.println(i.toString());
        }
    }
}
