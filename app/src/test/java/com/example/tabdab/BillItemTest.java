package com.example.tabdab;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillItemTest {
    @Test
    public void constructor_test() {
        double price = 1;
        String name = "test";
        BillItem item = new BillItem(price, name);
        assertEquals(1, item.getPrice(), .001);
        assertEquals("test", item.getName());

        name = "";
        item = new BillItem(price, name);
        assertEquals("", item.getName());
    }

    @Test
    public void constructor_test2() {
        BillItem item = new BillItem();
        assertEquals(0.0, item.getPrice(), 0);
        assertEquals("", item.getName());
    }

    @Test
    public void toString_test() {
        double price = 1;
        String name = "test";
        BillItem item = new BillItem(price, name);
        assertEquals("test: 1.0", item.toString());
    }
}
