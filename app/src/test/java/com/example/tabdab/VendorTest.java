package com.example.tabdab;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
public class VendorTest {

    /**
     * Test Vendor Default constructor
     * create default object and
     * assert equals test
     * all parameters should be blank
     */
    @Test
    public void testDefaultConstructor() {
        Vendor vendor = new Vendor();

        //assert equals vendor ID
        assertEquals("", vendor.getVendorId());

        //assert equals vendor name
        assertEquals("", vendor.getName());

        //assert equals menu
        List<BillItem> menu = new ArrayList<>();
        assertEquals(menu, vendor.getMenu());
    }

    /**
     * Test vendor with paramaterized vendor name
     * create vendor object with just paramater name
     * assert equals test
     * all parameters should be blank besides name
     */
    @Test
    public void testConstructorWithName() {
        //create string for name
        String vendorName = "Bobs Burgers";

        //create vendor object with just name param
        Vendor vendor = new Vendor(vendorName);

        //assert equals name
        assertEquals(vendorName, vendor.getName());
        //test name in a different way
        assertEquals("Bobs Burgers", vendor.getName());
        assertNotEquals("Bobbys Burgers", vendor.getName());

        //assert equals vendor ID
        assertEquals("", vendor.getVendorId());

        //assert equals menu
        List<BillItem> menu = new ArrayList<>();
        assertEquals(menu, vendor.getMenu());
    }

    /**
     * Test vendor with paramertized vendor id and name
     * Create vendor objet with paramters id and name
     * assert equals test
     * menu will be blank
     */
    @Test
    public void testConstructorWithSomeParams() {
        String vendorName = "Dog Hut";
        String vendorId = "-MPig_oRflyYiyF7B-KQ";

        //create vendor object with params: id and name
        Vendor vendor = new Vendor(vendorId, vendorName);

        //assert equals vendor ID 2 different ways
        assertEquals(vendorId, vendor.getVendorId());
        assertEquals("-MPig_oRflyYiyF7B-KQ", vendor.getVendorId());

        //assert equals vendor name 2 different ways
        assertEquals(vendorName, vendor.getName());
        assertEquals("Dog Hut", vendor.getName());

        //assert equals menu
        List<BillItem> menu = new ArrayList<>();
        assertEquals(menu, vendor.getMenu());
    }

    /**
     * Test vendor with full parametrized constructor
     * Create vendor Object with all parameters
     * assert euqls test on all params
     */
    @Test
    public void testParamConstructor() {
        String vendorName = "RedBull Hut";
        String vendorId = "-MPig_oRflyYiyF7B-KQ";
        List<BillItem> menu = new ArrayList<>();

        //create and add item to menu
        String itemName = "RedBull";
        double price = 5.0;
        BillItem item = new BillItem(price, itemName);
        menu.add(item);

        //create and add another item to menu
        itemName = "BlueBerry RedBull";
        price = 6.0;
        item = new BillItem(price, itemName);
        menu.add(item);

        //create and add last item to menu
        itemName = "Sugar Free RedBull";
        price = 5.5;
        item = new BillItem(price, itemName);
        menu.add(item);

        //create venodr object with all parameters
        Vendor vendor = new Vendor(vendorId, vendorName, menu);

        //assert equals vendor ID 2 different ways
        assertEquals(vendorId, vendor.getVendorId());
        assertEquals("-MPig_oRflyYiyF7B-KQ", vendor.getVendorId());

        //assert equals vendor name 2 different ways
        assertEquals(vendorName, vendor.getName());
        assertEquals("RedBull Hut", vendor.getName());

        //assert equals vendor menu
        assertEquals(menu, vendor.getMenu());
    }

    /**
     * Test set vendor ID
     * create new default vendor object
     * set a new vendor id and test it
     * repeat setter to ensure success
     */
    @Test
    public void testSetVendorId() {
        Vendor vendor = new Vendor();
        String vendorId = "-MPig_oRflyYiyF7P-KQ";

        //set the vendorId through assigned vendorId string and assert equal test
        vendor.setVendorId(vendorId);
        assertEquals(vendorId, vendor.getVendorId());

        //set the vendorId through created string and assert equal test
        vendor.setVendorId("-MLjg_oRflyYiyF7P-KQ");
        assertNotEquals(vendorId, vendor.getVendorId());
        assertEquals("-MLjg_oRflyYiyF7P-KQ", vendor.getVendorId());
    }

    /**
     * Test set Vendor name
     * create new default vendor object
     * set a new vendor name and test it
     * repeat setter and test to ensure success
     */
    @Test
    public void testSetVendorName() {
        Vendor vendor = new Vendor();
        String name = "McDubs";

        //set the vendor name through assigned name string and assert equal test
        vendor.setName(name);
        assertEquals(name, vendor.getName());

        //set the name through created string and assert equal test
        vendor.setName("China hut #1");
        assertNotEquals(name, vendor.getName());
        assertEquals("China hut #1", vendor.getName());
    }

    /**
     * Test Vendor set menu
     * create new default vendor object
     * create a menu and test it
     * append to it and test to ensure success
     */
    @Test
    public void testSetMenu() {
        Vendor vendor = new Vendor();
        List<BillItem> menu = new ArrayList<>();

        //create a single item menu and test it
        String itemName = "Hot-Dog";
        double price = 5.0;
        BillItem item = new BillItem(price, itemName);
        menu.add(item);
        vendor.setMenu(menu);
        //vendor menu should be the same as the created menu here
        assertEquals(menu, vendor.getMenu());

        //append the menu and reset it
        itemName = "Monster Taco";
        price = 3.50;
        item = new BillItem(price, itemName);
        menu.add(item);
        vendor.setMenu(menu);
        //vendor menu should be the same ass the created menu here
        assertEquals(menu, vendor.getMenu());

        //append one more item and reset it
        itemName = "Egg Roll";
        price = 2.0;
        item = new BillItem(price, itemName);
        menu.add(item);
        vendor.setMenu(menu);
        //vendor menu should be the same ass the created menu here
        assertEquals(menu, vendor.getMenu());
    }

    /**
     * Test vendor menuToString
     * create a menu and run the toString
     * create a string to replicate results
     */
    @Test
    public void testMenuToString() {
        Vendor vendor = new Vendor();
        List<BillItem> menu = new ArrayList<>();

        //create a single item menu
        String itemName = "Ice Cream";
        double price = 5.0;
        BillItem item = new BillItem(price, itemName);
        menu.add(item);
        //append another item
        itemName = "SpongeBob Popsicle";
        price = 3.50;
        item = new BillItem(price, itemName);
        menu.add(item);
        vendor.setMenu(menu);

        //create the anticipated String
        String ret = "";
        for (int i = 0; i < menu.size(); i++) {
            ret += menu.get(i).getName() + ": " + menu.get(i).getPrice() + "\n";
        }
        //test it
        assertEquals(ret, vendor.menuToString());
    }
}
