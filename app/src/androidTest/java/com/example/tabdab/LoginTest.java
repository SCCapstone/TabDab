package com.example.tabdab;


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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

  @Rule
  public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

  @Rule
  public GrantPermissionRule mGrantPermissionRule =
          GrantPermissionRule.grant(
                  "android.permission.CAMERA");

  @Test
  public void loginTestInvalidUser() {
    String badEmail = "bademail";
    String badPass = "badpass";

    onView(withId(R.id.LogEmail)).perform(typeText(badEmail));
    onView(withId(R.id.LogPass)).perform(typeText(badPass));
    onView(withId(R.id.loginButton)).perform(click());

    onView(withId(R.id.LogPass)).check(matches(hasErrorText("Sign in credentials do not match existing user. Please try again or register if you do not already have an account.")));
  }

  @Test
  public void loginTestEmptyEmail() {
    String empty = "";
    String password = "test";

    onView(withId(R.id.LogEmail)).perform(typeText(empty));
    onView(withId(R.id.LogPass)).perform(typeText(password));
    onView(withId(R.id.loginButton)).perform(click());

    onView(withId(R.id.LogEmail)).check(matches(hasErrorText("Email Required")));
  }

  @Test
  public void loginTestEmptyPassword() {
    String email = "test";
    String password = "";

    onView(withId(R.id.LogEmail)).perform(typeText(email));
    onView(withId(R.id.LogPass)).perform(typeText(password));
    onView(withId(R.id.loginButton)).perform(click());

    onView(withId(R.id.LogPass)).check(matches(hasErrorText("Password Required")));
  }

  @Test
  public void launchRegisterActivity() {
    Intents.init();
    onView(withId(R.id.registerButton)).perform(click());
    intended(hasComponent(CreateAccount.class.getName()));
  }
}
