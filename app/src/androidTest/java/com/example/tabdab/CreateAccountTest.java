package com.example.tabdab;
import android.util.Log;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateAccountTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA");

    @Test
    public void createAccountTestInvalidEmail() {
        String bademail = "bademail";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(bademail));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uEmail)).check(matches(hasErrorText("Valid Email Address Required")));
    }

    @Test
    public void createAccountTestEmptyEmail() {
        String emptyEmail = "";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(emptyEmail));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uEmail)).check(matches(hasErrorText("Valid Email Address Required")));
    }

    @Test
    public void createAccountTestInvalidPassword() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  badPassword = "123456";
        String cardNum = "1234123412341234";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(badPassword));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uPassword)).check(matches(hasErrorText("Password must be at least 8 characters")));
    }

    @Test
    public void createAccountTestEmptyPassword() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  emptyPassword = "";
        String cardNum = "1234123412341234";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(emptyPassword));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uPassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void createAccountTestInvalidCardSmall() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  emptyPassword = "";
        String badCardNum = "12341234";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(emptyPassword));
        onView(withId(R.id.uCardNum)).perform(typeText(badCardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCardNum)).check(matches(hasErrorText("Please enter valid card number")));
    }

    @Test
    public void createAccountTestInvalidCardBig() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String badCardNum = "12341234123412341234123";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(badCardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCardNum)).check(matches(hasErrorText("Please enter valid card number")));
    }
    @Test
    public void createAccountTestInvalidCard() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String badCardNum = "hellothere";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(badCardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCardNum)).check(matches(hasErrorText("Please enter valid card number")));
    }

    @Test
    public void createAccountTestEmptyCard() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String emptyCardNum = "";
        String  expDate = "12/3456";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(emptyCardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCardNum)).check(matches(hasErrorText("Please enter valid card number")));
    }

    @Test
    public void createAccountTestEmptyExpDate() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  emptyExpDate = "";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(emptyExpDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uExpDate)).check(matches(hasErrorText("Please enter valid expiration date")));
    }
    @Test
    public void createAccountTestInvalidExpDate() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  badExpDate = "12/02/2022";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(badExpDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uExpDate)).check(matches(hasErrorText("Please enter valid expiration date")));
    }

    @Test
    public void createAccountTestExpiredExpDate() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expiredExpDate = "12/2020";
        String cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expiredExpDate));
        onView(withId(R.id.uCVV)).perform(typeText(cvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uExpDate)).check(matches(hasErrorText("Please enter valid expiration date")));
    }

    @Test
    public void createAccountTestEmptyCVV() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expDate = "12/2022";
        String emptyCvv = "";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(emptyCvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCVV)).check(matches(hasErrorText("Please enter valid CVV")));
    }

    @Test
    public void createAccountTestInvalidCVV() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expDate = "12/2022";
        String badCvv = "12345";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(badCvv));
        onView(withId(R.id.btnRegister)).perform(click());

        onView(withId(R.id.uCVV)).check(matches(hasErrorText("Please enter valid CVV")));
    }

    @Test
    public void createAccount() {
        String email = "johndoe@gmail.com";
        String firstName = "John";
        String  lastName = "Doe";
        String  password = "12345678";
        String cardNum = "1234123412341234";
        String  expDate = "12/2022";
        String Cvv = "123";


        onView(withId(R.id.uFirstName)).perform(typeText(firstName));
        onView(withId(R.id.uLastName)).perform(typeText(lastName));
        onView(withId(R.id.uEmail)).perform(typeText(email));
        onView(withId(R.id.uPassword)).perform(typeText(password));
        onView(withId(R.id.uCardNum)).perform(typeText(cardNum));
        onView(withId(R.id.uExpDate)).perform(typeText(expDate));
        onView(withId(R.id.uCVV)).perform(typeText(Cvv));


        Intents.init();
        onView(withId(R.id.btnRegister)).perform(click());
        intended(hasComponent(Login.class.getName()));
    }

}