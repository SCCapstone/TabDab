package com.example.tabdab;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
public class UserTest {
    @Test
    public void constructorTest1(){
        String firstName = "Bob";
        String lastName = "Saget";
        String email = "BobSaget@gmail.com";
        boolean isVendor = false;
        String vendorId = "700";
        String cardNum = "405694564";
        String expDate = "04/2021";
        String CVV = "123";
        List<Bill> pastPayments = new ArrayList<>();
        BillItem item = new BillItem(1.0,"test" );
        Bill bill = new Bill();
        bill.addBillItem(item);
        pastPayments.add(bill);

        User user = new User(firstName, lastName, email,
                isVendor, vendorId, cardNum, expDate, CVV);
        user.setPastPayments(pastPayments);

        //firstName
        assertEquals("Bob", user.getFirstName());

        //lastName
        assertEquals("Saget", user.getLastName());

        //email
        assertEquals("BobSaget@gmail.com", user.getEmail());

        //isVendor
        assertEquals(false, user.getIsVendor());

        //vendorId
        assertEquals("700", user.getVendorID());

        //cardNum
        assertEquals("405694564", user.getCardNum());

        //expDate
        assertEquals("04/2021", user.getExpDate());

        //CVV
        assertEquals("123", user.getCVV());

        //PastPayments
        assertEquals(pastPayments.toArray()[0],pastPayments.toArray()[0]);
        assertEquals(pastPayments, user.getPastPayments());

        //try again but replace the first name
        firstName = "";
        user = new User(firstName, lastName, email,
                isVendor, vendorId, cardNum, expDate, CVV) ;

        assertEquals("", user.getFirstName());
    }


    @Test
    public void constructorTest2() { //this test will have the isVendor set to True

        String firstName = "John";
        String lastName = "Stamos";
        String email = "JohnStamos@gmail.com";
        boolean isVendor = true;
        String vendorId = "420";
        String cardNum = "6798043";
        String expDate = "12/2100";
        String CVV = "864";
        List<Bill> pastPayments = new ArrayList<>();
        BillItem item = new BillItem(56473,"Expensive" );
        Bill bill = new Bill();
        bill.addBillItem(item);
        pastPayments.add(bill);

        User user = new User(firstName, lastName, email,
                isVendor, vendorId, cardNum, expDate, CVV);
        user.setPastPayments(pastPayments);

        //firstName
        assertEquals("John", user.getFirstName());

        //lastName
        assertEquals("Stamos", user.getLastName());

        //email
        assertEquals("JohnStamos@gmail.com", user.getEmail());

        //isVendor
        assertEquals(true, user.getIsVendor());

        //vendorId
        assertEquals("420", user.getVendorID());

        //cardNum
        assertEquals("6798043", user.getCardNum());

        //expDate
        assertEquals("12/2100", user.getExpDate());

        //CVV
        assertEquals("864", user.getCVV());

        //PastPayments
        assertEquals(pastPayments.toArray()[0],pastPayments.toArray()[0]);
        assertEquals(pastPayments, user.getPastPayments());

        //lets change the first name again
        firstName = "";
        user = new User(firstName, lastName, email,
                isVendor, vendorId, cardNum, expDate, CVV) ;
        assertEquals("", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        User user = new User();
        user.setFirstName("Tyler");
        assertEquals("Tyler",user.getFirstName());

        user.setFirstName("Max");
        assertEquals("Max", user.getFirstName());

        user.setFirstName("Cam");
        assertEquals("Cam", user.getFirstName());

        user.setFirstName("Riley");
        assertEquals("Riley", user.getFirstName());

        user.setFirstName("Fei");
        assertEquals("Fei", user.getFirstName());
    }

    @Test
    public void testSetLastName() {
        User user = new User();
        user.setLastName("Shatley");
        assertEquals("Shatley", user.getLastName());

        user.setLastName("Hensler");
        assertEquals("Hensler", user.getLastName());

        user.setLastName("Knox");
        assertEquals("Knox", user.getLastName());

        user.setLastName("Conant");
        assertEquals("Conant", user.getLastName());

        user.setLastName("Zhu");
        assertEquals("Zhu", user.getLastName());
    }

    @Test
    public void testSetEmail() {
        User user = new User();
        user.setEmail("TylerShatley@gmail.com");
        assertEquals("TylerShatley@gmail.com", user.getEmail());

        user.setEmail("MaxHensler@yahoo.com");
        assertEquals("MaxHensler@yahoo.com", user.getEmail());

        user.setEmail("CamKnox@hotmail.com");
        assertEquals("CamKnox@hotmail.com", user.getEmail());

        user.setEmail("RileyConant@aol.com");
        assertEquals("RileyConant@aol.com",user.getEmail());

        user.setEmail("FeiZhu@email.sc.edu");
        assertEquals("FeiZhu@email.sc.edu", user.getEmail());
    }

    @Test
    public void testSetIsVendor() {
        User user = new User();
        user.setIsVendor(false);
        assertEquals(false, user.getIsVendor());

        user.setIsVendor(true);
        assertEquals(true, user.getIsVendor());
    }

    @Test
    public void testSetVendorId() {
        User user = new User();
        user.setVendorID("69420");
        assertEquals("69420", user.getVendorID());

        user.setVendorID("666");
        assertEquals("666", user.getVendorID());
    }

    @Test
    public void testSetCardNum() {
        User user = new User();
        user.setCardNum("234234234");
        assertEquals("234234234", user.getCardNum());

        user.setCardNum("9373495702");
        assertEquals("9373495702", user.getCardNum());
    }

    @Test
    public void testSetExpDate() {
        User user = new User();
        user.setExpDate("01/2020");
        assertEquals("01/2020", user.getExpDate());

        user.setExpDate("02/8473");
        assertEquals("02/8473", user.getExpDate());
    }

    @Test
    public void testSetCVV() {
        User user = new User();
        user.setCVV("987");
        assertEquals("987", user.getCVV());

        user.setCVV("123");
        assertEquals("123", user.getCVV());
    }

    @Test
    public void testSetPastPayments() {
        List<Bill> pastPayments = new ArrayList<>();
        BillItem item = new BillItem(1.0,"test" );
        Bill bill = new Bill();
        bill.addBillItem(item);
        pastPayments.add(bill);
        User user = new User();
        user.setPastPayments(pastPayments);
        assertEquals(pastPayments,user.getPastPayments());
    }

    @Test
    public void testToString() {
        User user = new User();
        String firstName = "Ron";
        String lastName = "Jeremey";
        String email = "RonJeremey@gmail.com";
        boolean isVendor = false;
        String vendorId = "1000";
        String cardNum = "74639263864";
        String expDate = "09/2120";
        String CVV = "124";
        user = new User(firstName, lastName, email,
                isVendor, vendorId, cardNum, expDate, CVV) ;
        assertEquals("Ron Jeremey" + "\n" + "RonJeremey@gmail.com" + "\n" + false + "\n" + "1000" + "\n" +
                "74639263864" + "\n" + "09/2120" + "\n" + "124", user.toString());
    }
}
