package com.example.tabdab;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void constructorTest1(){ //this constructor test has the boolean
        String firstName = "Bob";
        String lastName = "Saget";
        String email = "BobSaget@gmail.com";
        boolean isVendor = false;
        String vendorId = "700";

        User user = new User(firstName, lastName, email,
                isVendor, vendorId);

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

        //try again but replace the first name
        firstName = "";
        user = new User(firstName, lastName, email,
        isVendor, vendorId);

        assertEquals("", user.getFirstName());
    }
    @Test
    public void constructorTest2() { //this test will have the isVendor set to True

        String firstName = "John";
        String lastName = "Stamos";
        String email = "JohnStamos@gmail.com";
        boolean isVendor = true;
        String vendorId = "420";

        User user = new User(firstName, lastName, email,
                isVendor, vendorId);

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

        //lets change the first name again
        firstName = "";
        user = new User(firstName, lastName, email,
                isVendor, vendorId);
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
    public void testToString() {
        User user = new User();
        String firstName = "Ron";
        String lastName = "Jeremey";
        String email = "RonJeremey@gmail.com";
        boolean isVendor = false;
        String vendorId = "1000";
        user = new User(firstName, lastName, email, isVendor, vendorId);
        assertEquals("Ron Jeremey" + "\n" + "RonJeremey@gmail.com" + "\n" + false + "\n" + "1000", user.toString());
    }
}
