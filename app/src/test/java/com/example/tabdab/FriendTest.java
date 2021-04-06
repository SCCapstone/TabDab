package com.example.tabdab;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
public class FriendTest {

    /**
     * Test Friend Default Constructor
     * all parameters should be blank strings
     * assert equals on them all
     */
    @Test
    public void testDefaultConstructor() {
        Friend friend = new Friend();
        String firstName = "";
        String lastName = "";
        String email = "";

        //test firstName
        assertEquals(firstName, friend.getFirstName());

        //test lastName
        assertEquals(lastName, friend.getLastName());

        //test email
        assertEquals(email, friend.getEmail());
    }

    /**
     * Test paramterized Friend Constructor
     * Create paramterized friend
     * assert equals on all variables
     */
    @Test
    public void testParametrizedConstructor() {
        String firstName = "Tyler";
        String lastName = "Shatley";
        String email = "TShatley@Email.sc.edu";

        Friend friend = new Friend(firstName, lastName, email);

        //assert equals first name
        assertEquals(firstName, friend.getFirstName());

        //assert equals last name
        assertEquals(lastName, friend.getLastName());

        //assert equals email
        assertEquals(email, friend.getEmail());
    }

    /**
     * Testing the Friend first name setter
     * Create default friend, set the first name
     * assert equals test
     * switch it up and test again
     */
    @Test
    public void testSetFirstName() {
        Friend friend = new Friend();

        //set the first name
        friend.setFirstName("Billy");
        //assert equals with string "Billy"
        assertEquals("Billy", friend.getFirstName());

        //reset the first name in a different way
        String firstName = "Bobby";
        friend.setFirstName(firstName);

        //assert equals in two different ways
        assertEquals(firstName, friend.getFirstName());
        assertEquals("Bobby", friend.getFirstName());
    }

    /**
     * Testing the Friend last name setter
     * Create default friend, set the last name
     * assert equals test
     * switch it up and test again
     */
    @Test
    public void testSetLastName() {
        Friend friend = new Friend();

        //set the Last name
        friend.setLastName("Whitney");
        //assert equals with string "Whitney"
        assertEquals("Whitney", friend.getLastName());

        //reset the last name in a different way
        String lastName = "Huseki";
        friend.setLastName(lastName);

        //assert equals in two different ways
        assertEquals(lastName, friend.getLastName());
        assertEquals("Huseki", friend.getLastName());
    }

    /**
     * Testing the Friend email setter
     * Create default friend, set the email
     * assert equals test
     * switch it up and test again
     */
    @Test
    public void testSetEmail() {
        Friend friend = new Friend();

        //set the Email
        friend.setEmail("WhitneyBoi@gmail.com");
        //assert equals with string "WhitneyBoi@gmail.com"
        assertEquals("WhitneyBoi@gmail.com", friend.getEmail());

        //reset the email in a different way
        String email = "HusekiBoi@gmail.com";
        friend.setEmail(email);

        //assert equals in two different ways
        assertEquals(email, friend.getEmail());
        assertEquals("HusekiBoi@gmail.com", friend.getEmail());
    }
}
