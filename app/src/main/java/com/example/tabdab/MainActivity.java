package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // https://github.com/yuriy-budiyev/code-scanner

    // Define scanner and and scanner result string for use throughout the activity
    CodeScanner codeScanner;
    public static final String EXTRA_MESSAGE = "com.example.android.tabdab.extra.MESSAGE";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private int currentFragment = R.layout.fragment_main;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize nav bar
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        // Set the home screen as first activity
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, new MainFragment()).commit();

        // Define and create camera and scanner
        CodeScannerView scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannerView);
        final Intent intent = new Intent(this, BillView.class);

        // Decode the QR code
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                // Run in a separate thread for better app performance
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Launch the bill view activity when a QR code is decoded
                        String message = result.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                    }
                });
            }
        });

        // Start camera (qr code scanner)
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//    }

    @Override
    public void onBackPressed() {
        // If the drawer is open and back is pressed, close it.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Start camera when the user comes back to the app
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//        if (currentFragment == item.getItemId()) {
//            drawer.closeDrawer(GravityCompat.START);
//            return false;
//        }
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navi_home:
                startActivity(new Intent(MainActivity.this, AccountInformation.class));
                break;
            case R.id.navi_scan:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.navi_setting:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SettingsFragment()).commit();
                break;
            case R.id.navi_vendor:
                startActivity(new Intent(MainActivity.this, EditMenu.class));
                break;
            case R.id.navi_exit:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
//        if (fragment != null) {
//            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
//            drawer.closeDrawer(GravityCompat.START);
//            return true;
//        }
        return false;
    }
}