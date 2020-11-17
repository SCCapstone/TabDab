package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BillView extends AppCompatActivity {
    TextView itemizedView;
    TextView grandTotalView;
    TextView editTip;
    Button butAddTip;
    Bill bill;
    double tip = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

        // Setup UI layout
        itemizedView = findViewById(R.id.itemized_view);
        grandTotalView = findViewById(R.id.grand_total_view);
        editTip = findViewById(R.id.editTip);
        butAddTip = findViewById(R.id.addTipButton);

        // Get and display data from qr scanner activity
        Intent intent = getIntent();
        String qrResult = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        bill = new Bill(qrResult);

        // Set UI components
        itemizedView.setText(bill.toString());
        grandTotalView.setText(Double.toString(bill.getGrandTotal()));

        // Add tip
        butAddTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                bill.setTip(Double.parseDouble(editTip.getText().toString()));
                grandTotalView = findViewById(R.id.grand_total_view);
                grandTotalView.setText(Double.toString(bill.getGrandTotal() + bill.getTip()));
                itemizedView.setText(bill.toString());
            }
        });


    }

    public void pay (View view) {
        //Toast payMessage = Toast.makeText(this, R.string.pay_message, Toast.LENGTH_SHORT);
        //payMessage.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("payments");
        ref.child(Calendar.getInstance().getTime().toString()).setValue(Bill.toJSON(bill));
    }
}
