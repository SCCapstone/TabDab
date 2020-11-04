package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScanOrShow extends AppCompatActivity {
    private Button butCustomer, butVendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_or_show);
    }

    public void launchScanner (View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void launchBillCreator (View view) {
        startActivity(new Intent(getApplicationContext(), BillCreator.class));
    }
}