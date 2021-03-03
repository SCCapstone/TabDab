package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
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
    }

    // Start camera when the user comes back to the app
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}