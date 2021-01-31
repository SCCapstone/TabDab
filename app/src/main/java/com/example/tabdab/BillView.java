package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BillView extends AppCompatActivity {
    TextView itemizedView;
    TextView grandTotalView;
    TextView editTip;
    Button butAddTip;
    Bill bill;
    double tip = 0.0;

    String userId;
    DatabaseReference database;
    FirebaseUser userRef;
    User user;

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
        bill = Bill.fromJson(qrResult);

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
        userRef = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        userId = userRef.getUid();
    }

}
