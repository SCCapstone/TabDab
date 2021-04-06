package com.example.tabdab;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
public class BillTest {

    /**
     * Constructor test 1
     * test to make sure the constructor
     * creates and returns a bill properly
     */
    @Test
    public void constructorTest1() {
        String vendor = "Taco Stand";
        String vendorId = "-MWvg_oRflyYiyF7B-KQ";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4;
        String name = "Taco";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 4;
        double tip = 10.5;

        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);
        bill.setVendor("Taco Stand");

        //vendor
        assertEquals("Taco Stand", bill.getVendor());

        //vendorId
        assertEquals("-MWvg_oRflyYiyF7B-KQ", bill.getVendorId());

        //itemized bill
        assertEquals(itemizedBill, bill.getItemizedBill());

        //grandTotal
        assertEquals(grandTotal, bill.getGrandTotal(), 0.001);

        //tip
        assertEquals(tip, bill.getTip(), 0.001);
    }

    /**
     * Constructor test 2
     * test to make sure the constructor
     * creates and returns a bill properly
     */
    @Test
    public void constructor_test2 () {
        String vendor = "Burger Stand";
        String vendorId = "-MWvrHIEq-fwgwUv6WPr";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 8.0;
        String name = "Hamburger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 8.0;
        double tip = 10.1;

        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);
        bill.setVendor("Burger Stand");

        //vendor
        assertEquals("Burger Stand", bill.getVendor());

        //vendorId
        assertEquals("-MWvrHIEq-fwgwUv6WPr", bill.getVendorId());

        //itemized bill
        assertEquals(itemizedBill, bill.getItemizedBill());

        //grandTotal
        assertEquals(grandTotal, bill.getGrandTotal(), 0.001);

        //tip
        assertEquals(tip, bill.getTip(), 0.001);
    }

    /**
     * Set vendor Name test
     * set the vendor name
     * assert equals to test
     * change it up to ensure success
     */
    @Test
    public void testSetVendor() {
        Bill bill = new Bill();

        bill.setVendor("Pizza Stand");
        assertEquals("Pizza Stand", bill.getVendor());
        assertNotEquals("Puzza Stand", bill.getVendor());

        bill.setVendor("Burger Stand");
        assertEquals("Burger Stand", bill.getVendor());
        assertNotEquals("Burher Stand", bill.getVendor());
    }

    /**
     * Set vendorId test
     * set the vendor Id
     * assert equals to test
     * change it up to ensure success
     */
    @Test
    public void testSetVendorId() {
        Bill bill = new Bill();

        //first test
        bill.setVendorId("-MWvg_oRflyYiyF7B-KQ");
        assertEquals("-MWvg_oRflyYiyF7B-KQ", bill.getVendorId());
        assertNotEquals("-MWvg_oRflyYiyF7B-KP", bill.getVendorId());

        //second test
        bill.setVendorId("-MWvrHIEq-fwgwUv6WPr");
        assertEquals("-MWvrHIEq-fwgwUv6WPr", bill.getVendorId());
        assertNotEquals("-MWvrHIEq-fwgwUv6WPu", bill.getVendorId());
    }

    /**
     * Date test
     * create new date, MM/dd/yyyy
     * create new bill,
     * defaults to current date
     * assert equals to test it
     */
    @Test
    public void testDate() {
        Bill bill = new Bill();
        //new Date MM/dd/yyyy
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        //test it out
        assertEquals(date, bill.getDate());
    }

    /**
     * add billItems test
     * add items to the bill
     * append more
     * assert equals to test
     */
    @Test
    public void testAddBillItems() {
        Bill bill = new Bill();

        //first test
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 10.00;
        String name = "Double Burger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.addBillItem(item);
        Assert.assertEquals(itemizedBill, bill.getItemizedBill());

        //add more to it
        name = "Triple Burger";
        price = 12.50;
        item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.addBillItem(item);
        Assert.assertEquals(itemizedBill, bill.getItemizedBill());
    }

    /**
     * set grand total test
     * set grand total
     * test it
     * reset and test again
     */
    @Test
    public void testSetGrandtotal() {
        Bill bill = new Bill();
        List<BillItem> itemizedBill = new ArrayList<>();

        //first item in bill
        double price = 10.0;
        String name = "Borger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.setItemizedBill(itemizedBill);
        bill.setGrandTotal();
        assertEquals(10.0, bill.getGrandTotal(), 0.001);

        //append another item in bill to ensure success
        price = 12.0;
        name = "X-treme borger";
        item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.setItemizedBill(itemizedBill);
        bill.setGrandTotal();
        assertEquals(22.0, bill.getGrandTotal(), 0.001);
    }

    /**
     * Set itemized bill test
     * create bill
     * test it
     * add more, set bill, test it again
     */
    @Test
    public void testSetItemizedBill() {
        Bill bill = new Bill();
        List<BillItem> itemizedBill = new ArrayList<>();

        //create first item in bill
        double price = 11.0;
        String name = "Big boy taco";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.setGrandTotal();
        bill.setItemizedBill(itemizedBill);
        assertEquals(itemizedBill, bill.getItemizedBill());

        //add another item to the bill and set it again
        price = 12.0;
        name = "Biggest boy taco";
        item = new BillItem(price, name);
        itemizedBill.add(item);
        bill.setGrandTotal();
        bill.setItemizedBill(itemizedBill);
        assertEquals(itemizedBill, bill.getItemizedBill());
    }

    /**
     * Set tip test
     * create bill
     * add a tip and test it
     * try again with a different tip
     */
    @Test
    public void testSetTip() {
        Bill bill = new Bill();

        //first tip test
        double tip = 22.90;
        bill.setTip(tip);
        assertEquals(tip, bill.getTip(), 0.001);

        //second tip test
        tip = 14.0;
        bill.setTip(tip);
        assertEquals(tip, bill.getTip(), 0.001);
    }

    /**
     * Bill To String test
     * create bill
     * create string with all parameters,
     * test it aganist toString
     */
    @Test
    public void testToString() {

        String vendor = "Taco Stand";
        String vendorId = "-MWvg_oRflyYiyF7B-KQ";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4.0;
        String name = "Taco";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 14.5;
        double tip = 10.5;

        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);
        bill.setVendor("Taco Stand");
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        String test = date + "\n" + vendor + "\n";
        for (int i = 0; i < itemizedBill.size(); ++i) {
            test += itemizedBill.get(i).getName() + ": $" + itemizedBill.get(i).getPrice() + "\n";
        }
        test += "Tip: " + tip;
        assertEquals(test, bill.toString());
    }

    /**
     * itemized bill to String test
     * create itemized bill/ bill
     * create string with all neccessary parameters
     * test it with toString
     */
    @Test
    public void testItemizedToString() {
        String vendor = "Taco Stand";
        String vendorId = "-MWvg_oRflyYiyF7B-KQ";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4.0;
        String name = "Taco";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        price = 6.09;
        name = "Monster Taco";
        item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 14.5;
        double tip = 10.5;
        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);

        String ret = "";
        for (int i = 0; i < itemizedBill.size(); ++i) {
            ret += itemizedBill.get(i).getName() + ": " +
                    itemizedBill.get(i).getPrice() + "\n";
        }
        ret += "Tip: " + tip;
        assertEquals(ret, bill.itemizedBillToString());
    }
}
