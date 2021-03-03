package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreviousPayments extends AppCompatActivity {
  ScrollView scroller;
  TextView textView;
  String prevPaymentsStr;

  DatabaseReference database;
  FirebaseUser userRef;
  User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_previous_payments);

    // UI elements
    scroller = findViewById(R.id.scroller);
    textView = findViewById(R.id.payments);

    // Database elements
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    database = FirebaseDatabase.getInstance().getReference().child("users").child(userRef.getUid());
    prevPaymentsStr = "";

    database.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        user = snapshot.getValue(User.class);
        List<Bill> prevPayments = user.getPastPayments();

        for (int i = 1; i < prevPayments.size(); i++) {
          prevPaymentsStr += prevPayments.get(i).toString() + "\n\n";
        }
        textView.setText(prevPaymentsStr);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }
}