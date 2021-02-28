package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PastPaymentsFragment extends Fragment {
  ScrollView scroller;
  TextView textView;
  String prevPaymentsStr;

  DatabaseReference database;
  FirebaseUser userRef;
  User user;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_past_payments, container, false);

    // UI elements
    scroller = view.findViewById(R.id.scroller);
    textView = view.findViewById(R.id.payments);

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

    return view;
  }

  public static PastPaymentsFragment newInstance () {return new PastPaymentsFragment();}
}