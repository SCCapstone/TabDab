package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import org.w3c.dom.Text;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    // https://github.com/yuriy-budiyev/code-scanner

    // Define scanner and and scanner result string for use throughout the activity
    CodeScanner codeScanner;
    public static final String EXTRA_MESSAGE = "com.example.android.tabdab.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define and create camera and scanner
        CodeScannerView scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannerView);
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

        //Creating the bottom navigation menu
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        //Opening the home fragment which is the account information
        openFragment(new HomeFragment());
        //Checking which button gets clicked to switch fragments
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        //openFragment(new HomeFragment());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.scannerView:
                        openFragment(new ScanFragment());
                        return true;
                    case R.id.setting:
                        openFragment(new SettingsFragment());
                        return true;
                }
                return false;
            }
        });
    }

    // Start camera when the user comes back to the app
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
    void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}