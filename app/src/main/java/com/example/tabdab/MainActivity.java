package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

    // Define scanner and textview for use throughout the activity
    CodeScanner codeScanner;
    TextView resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define and create camera and scanner
        CodeScannerView scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannView);
        resultData = findViewById(R.id.resultsOfQr);

        // Decode the QR code
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                // Run in a separate thread for better app performance
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set text view to data in decoded qr code
                        resultData.setText(result.getText());
                    }
                });
            }
        });

        // Start camera
        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    // Start camera when a user comes back to the app
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}