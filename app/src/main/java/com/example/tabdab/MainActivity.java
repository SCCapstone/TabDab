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
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
  private FrameLayout frameLayout;
  private FirebaseAuth fireAuth;
  SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fireAuth = FirebaseAuth.getInstance();

    sharedPreferences = getApplicationContext().getSharedPreferences("Preferences",0);

    // Initialize nav bar
    toolbar = findViewById(R.id.toolbar);
    drawer = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.nav_view);
    navigationView.bringToFront();
    frameLayout = findViewById(R.id.frame);  // Might need to cast to FrameLayout

    displayFragment();
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
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;
    Fragment fragment = null;

    switch (item.getItemId()) {
      case R.id.navi_past_payments:
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, PastPaymentsFragment.newInstance()).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_scan:
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, QrScannerFragment.newInstance()).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_setting:
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, SettingsFragment.newInstance()).commit();
        ft.addToBackStack(null);
        drawer.closeDrawer(GravityCompat.START);
        break;
      case R.id.navi_vendor:
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, VendorMenuFragment.newInstance()).commit();
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
      fm.beginTransaction().replace(R.id.frame, fragment).setReorderingAllowed(true).commit();
      drawer.closeDrawer(GravityCompat.START);
      return true;
    }
    return false;
  }

  public void displayFragment () {
    QrScannerFragment qrScanner = QrScannerFragment.newInstance();

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    // Add the MainFragment
    fragmentTransaction.add(R.id.fragment_container, qrScanner).addToBackStack(null).commit();
    isFragmentDisplayed = true;
  }
  public void closeFragment() {
    // Get the FragmentManager.

    FragmentManager fragmentManager = getSupportFragmentManager();
  }

  public void changeFragment (Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, fragment);
    fragmentTransaction.addToBackStack(null).commit();
  }

}