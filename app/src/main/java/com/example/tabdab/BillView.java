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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class BillView extends AppCompatActivity {
  TextView itemizedView;
  TextView grandTotalView;
  TextView editTip;
  Button butAddTip, butPay;
  Bill bill;

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
    butPay = findViewById(R.id.pay_button);

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
        if (!editTip.getText().toString().isEmpty()) {
          bill.setTip(Double.parseDouble(editTip.getText().toString()));
          grandTotalView = findViewById(R.id.grand_total_view);
          grandTotalView.setText(Double.toString(bill.getGrandTotal() + bill.getTip()));
          itemizedView.setText(bill.toString());
        } else {
          Toast.makeText(getApplicationContext(), "Cannot enter empty tip.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    butPay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View view) {
        userRef = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("users").child(userRef.getUid());

        database.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            user = snapshot.getValue(User.class);
            List<Bill> pastPayments = user.getPastPayments();
            pastPayments.add(bill);
            database.child("pastPayments").setValue(pastPayments);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("BillView.java", "pay method failure.");
          }
        });

        // Go back to the main activity
        Toast.makeText(getApplicationContext(), "Bill Payed!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), AccountInformation.class));
      }
    });


  }
}
