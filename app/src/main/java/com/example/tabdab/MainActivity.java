package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  private boolean isFragmentDisplayed = false;
  private Toolbar toolbar;
  private DrawerLayout drawer;
  private int currentFragment = R.layout.fragment_main;
  private NavigationView navigationView;
  FragmentManager fm;
  SharedPreferences sharedPreferences;

  private FrameLayout frameLayout;
  View header;
  TextView headerUser, headerEmail;
  String userEmail;

  User user;
  Vendor vendor;

  private FirebaseAuth fireAuth;
  DatabaseReference userDb;
  DatabaseReference vendorDb;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fireAuth = FirebaseAuth.getInstance();
    userEmail = fireAuth.getCurrentUser().getEmail().replace('.', '*');
    userDb = FirebaseDatabase.getInstance().getReference("users").child(userEmail);

    // For logout purposes
    sharedPreferences = getApplicationContext().getSharedPreferences("Preferences",0);

    // Initialize nav bar
    toolbar = findViewById(R.id.toolbar);
    drawer = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    navigationView.bringToFront();
    fm = getSupportFragmentManager();
    frameLayout = findViewById(R.id.fragment_container);

    // Header Info
    header = navigationView.getHeaderView(0);
    headerUser = header.findViewById(R.id.UserFire);
    headerEmail = header.findViewById(R.id.EmailFire);

    userDb.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        user = snapshot.getValue(User.class);

        // Set the header text
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
          headerUser.setText(user.getFirstName() + " " + user.getLastName());
          headerEmail.setText(userEmail.replace('*', '.'));
        }

        // Display the QR scanner
        displayFragment();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("MainActivity.java", error.getMessage());
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    setSupportActionBar(toolbar);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    // If the drawer is open and back is pressed, close it.
    if (this.drawer.isDrawerOpen(GravityCompat.START)) {
      this.drawer.closeDrawer(GravityCompat.START);
    } else if (fm.getBackStackEntryCount() == 0) {
      finishAffinity();
      finish();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    if (currentFragment == item.getItemId()) {
      drawer.closeDrawer(GravityCompat.START);
      return false;
    }

    FragmentTransaction ft;
    Fragment fragment = null;

    switch (item.getItemId()) {
      case R.id.navi_past_payments:
        ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, PastPaymentsFragment.newInstance()).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_scan:
        ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, QrScannerFragment.newInstance(user)).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_setting:
        ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, SettingsFragment.newInstance(user)).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_friends:
        ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, FriendsMenuFragment.newInstance(user)).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_vendor:
        ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, VendorMenuFragment.newInstance(user)).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_exit:
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN", null);
        editor.apply();
        fireAuth.signOut();

        startActivity(new Intent(getApplicationContext(), Login.class));
        break;
    }
    if (fragment != null) {
      fm.beginTransaction().replace(R.id.fragment_container, fragment).setReorderingAllowed(true).commit();
      drawer.closeDrawer(GravityCompat.START);
      return true;
    }
    return false;
  }

  public void displayFragment () {
    QrScannerFragment qrScanner = QrScannerFragment.newInstance(user);

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    // Add the MainFragment
    fragmentTransaction.add(R.id.fragment_container, qrScanner).commit();
    isFragmentDisplayed = true;
  }
}