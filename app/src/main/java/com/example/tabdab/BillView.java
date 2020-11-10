package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import static com.example.tabdab.Bill.decode;

public class BillView extends AppCompatActivity {

    public static final int GRAND_TOTAL_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

        // Get and display data from qr scanner activity
        Intent intent = getIntent();
        String qrResult = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Bill bill = new Bill(qrResult);

        // Set UI components
        TextView itemizedView = findViewById(R.id.itemized_view);
        TextView grandTotalView = findViewById(R.id.grand_total_view);
        itemizedView.setText(bill.toString());
        grandTotalView.setText(bill.getGrandTotalString());
    }

    public void pay (View view) {
        Toast payMessage = Toast.makeText(this, R.string.pay_message,
                                            Toast.LENGTH_SHORT);
        payMessage.show();
    }
}
