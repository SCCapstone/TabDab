package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class BillView extends AppCompatActivity {
    TextView itemizedView;
    TextView grandTotalView;
    TextView editTip;
    Button butAddTip;
    double tip = 0.0;
    Bill bill;

    // TODO make a constructor that doesn't need an input, initialize above then set the bill
    //  values in the onCreate method. Update the text view on button click

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

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

        // TODO fix this
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
        Toast payMessage = Toast.makeText(this, R.string.pay_message,
                                            Toast.LENGTH_SHORT);
        payMessage.show();
    }

    public void addTip (View view) {
        tip = Double.parseDouble(editTip.getText().toString());

    }
}
