package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
  //TextView textView;
  LinearLayout payments;
  String prevPaymentsStr;

  DatabaseReference database;
  FirebaseUser userRef;
  User user;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_past_payments, container, false);

    // UI elements
    scroller = view.findViewById(R.id.scroller);
    payments = view.findViewById(R.id.payments);
    //textView = view.findViewById(R.id.payments);

    // Database elements
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    database = FirebaseDatabase.getInstance().getReference().child("users").child(userRef.getUid());
    prevPaymentsStr = "";

    database.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        user = snapshot.getValue(User.class);
        List<Bill> prevPayments = user.getPastPayments();

        for (int i = prevPayments.size()-1; i > 0; i--) {
          TextView pastBill = new TextView(getContext());
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          params.setMargins(0,10,0,10);
          pastBill.setId(i);
          pastBill.setText(prevPayments.get(i).toString());
          pastBill.setTextColor(Color.WHITE);
          pastBill.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.login_button, null));
          pastBill.setLayoutParams(params);
          pastBill.setPadding(10,10,10,10);
          payments.addView(pastBill);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("PastPaymentsFragment", error.getMessage());
      }
    });

    return view;
  }

  public static PastPaymentsFragment newInstance () {return new PastPaymentsFragment();}
}