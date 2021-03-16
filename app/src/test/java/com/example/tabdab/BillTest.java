package com.example.tabdab;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
public class BillTest {
    @Test
    public void ConstructorTest1() {
        double grandTotal = 20.0;
        double tip = 1.5;
        List<BillItem> itemizedBill = new ArrayList<>();
       /* Bill bill = new Bill(itemizedBill, grandTotal, tip);

        assertEquals(grandTotal, bill.getGrandTotal(), 0.001);
        assertEquals(tip, bill.getTip(), 0.001);
        */
    }
}
