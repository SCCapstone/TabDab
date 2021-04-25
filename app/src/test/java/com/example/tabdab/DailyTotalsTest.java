package com.example.tabdab;
import org.junit.Assert;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static org.junit.Assert.*;
public class DailyTotalsTest {

    /**
     * Default constructor test
     * Create Default Daily Totals object
     * assert equals test
     */
    @Test
    public void testDefaultConstructor() {
        DailyTotals dt = new DailyTotals();
        String vendorId = "";
        List<Bill> previousPayments = new ArrayList<>();
        HashMap<String, List<BillItem>> totals = new HashMap<>();

        //test vendor id
        assertEquals(vendorId, dt.getVendorId());

        //previous payments
        assertEquals(previousPayments, dt.getPreviousPayments());

        //test totals
        assertEquals(totals, dt.getTotals());
    }

    @Test
    public void testParamConstructor() {
        String vendorId = "-MPig_oRflyYiyF7B-OQ";
        ArrayList<Bill> previousPayments = new ArrayList<>();
        HashMap<String, List<BillItem>> totals = new HashMap<>();
        String vendor = "Burger shack";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4;
        String name = "Burger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 4;
        double tip = 10.5;
        //bill creator
        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);

        previousPayments.add(bill);

        DailyTotals dt = new DailyTotals(vendorId,previousPayments,totals);



        //test vendor id
        assertEquals(vendorId, dt.getVendorId());

        //test previous payments
        assertEquals(previousPayments, dt.getPreviousPayments());

        //test totals
        assertEquals(totals, dt.getTotals());

    }


    @Test
    public void testSetVendorId() {
        DailyTotals dt = new DailyTotals();
        String vendorId = "-MPig_oRflyYiyF7B-OQ";

        dt.setVendorId(vendorId);

        assertEquals(vendorId, dt.getVendorId());

        dt.setVendorId("-OPij_oRflyYiyF7B-OQ");

        assertEquals("-OPij_oRflyYiyF7B-OQ",dt.getVendorId());
    }

    @Test
    public void testSetPreviousPayments() {
        DailyTotals dt = new DailyTotals();
        ArrayList<Bill> previousPayments = new ArrayList<>();
        HashMap<String, List<BillItem>> totals = new HashMap<>();
        String vendor = "Burger shack";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4;
        String name = "Burger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 4;
        double tip = 10.5;
        //bill creator
        Bill bill = new Bill(vendor, "-MPig_oRflyYiyF7B-OQ", itemizedBill, grandTotal, tip);

        previousPayments.add(bill);

        price = 10;
        name = "Double Burger";
        bill = new Bill(vendor, "-MPig_oRflyYiyF7B-OQ", itemizedBill, grandTotal, tip);

        previousPayments.add(bill);

        dt.setPreviousPayments(previousPayments);
        assertEquals(previousPayments, dt.getPreviousPayments());
    }

    @Test
    public void testSetTotals() {
        DailyTotals dt = new DailyTotals();
        HashMap<String, List<BillItem>> totals = new HashMap<>();
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4;
        String name = "Burger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);

        totals.put("Test", itemizedBill);

        dt.setTotals(totals);

        assertEquals(totals, dt.getTotals());
    }


    @Test
    public void testAddPreviousPayment() {
        String vendorId = "-MPig_oRflyYiyF7B-OQ";
        ArrayList<Bill> previousPayments = new ArrayList<>();
        DailyTotals dt = new DailyTotals();
        String vendor = "Burger shack";
        List<BillItem> itemizedBill = new ArrayList<>();
        double price = 4;
        String name = "Burger";
        BillItem item = new BillItem(price, name);
        itemizedBill.add(item);
        double grandTotal = 4;
        double tip = 10.5;
        //bill creator
        Bill bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);

        previousPayments.add(bill);

        dt.setPreviousPayments(previousPayments);

        assertEquals(previousPayments, dt.getPreviousPayments());


        name = " Double Burger";
        price = 10;
        bill = new Bill(vendor, vendorId, itemizedBill, grandTotal, tip);

        previousPayments.add(bill);

        dt.setPreviousPayments(previousPayments);

        assertEquals(previousPayments, dt.getPreviousPayments());
    }


}
