package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

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
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav_view);

        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_scanner, R.id.navigation_settings, R.id.navigation_vendor )
                .build();

        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);


    }

    // Start camera when the user comes back to the app
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}