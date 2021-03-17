package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BillViewFragment extends Fragment {
  TextView itemizedView, grandTotalView, editTip;
  Button butAddTip, butPay;
  Bill bill;

  DatabaseReference userDb, vendorDb, dailyTotalsDb;
  FirebaseUser userRef;
  User user;
  Vendor vendor;
  DailyTotals dailyTotals;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userDb = FirebaseDatabase.getInstance().getReference().child("users").child(userRef.getUid());
  }

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_bill_view, container, false);
    itemizedView = view.findViewById(R.id.itemized_view);
    grandTotalView = view.findViewById(R.id.grand_total_view);
    editTip = view.findViewById(R.id.editTip);
    butPay = view.findViewById(R.id.pay_button);
    butAddTip = view.findViewById(R.id.addTipButton);

    // Get and display data from qr scanner fragment
    String qrResult = getArguments().getString("qrResult", "");
    bill = Bill.fromJson(qrResult);
    vendorDb = FirebaseDatabase.getInstance().getReference("vendors").child(bill.getVendorId());

    // Set UI components
    itemizedView.setText(bill.toString());
    grandTotalView.setText(Double.toString(bill.getGrandTotal()));

    // Add tip
    butAddTip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View view) {
        bill.setTip(Double.parseDouble(editTip.getText().toString()));
        grandTotalView.setText(Double.toString(bill.getGrandTotal() + bill.getTip()));
        itemizedView.setText(bill.toString());
      }
    });

    butPay.setOnClickListener(new View.OnClickListener() {
      // Update the users past payments
      @Override
      public void onClick (View view) {
        // Get the user and update firebase with their payment
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            user = snapshot.getValue(User.class);
            List<Bill> pastPayments = user.getPastPayments();
            pastPayments.add(bill);
            userDb.child("pastPayments").setValue(pastPayments);

            Toast.makeText(getContext(), "Bill Payed!", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("BillView.java", "pay method failure.");
          }
        });

        // Update the daily totals for the vendor
        dailyTotalsDb = FirebaseDatabase.getInstance().getReference("daily_totals").child(bill.getVendorId());
        dailyTotalsDb.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            dailyTotals = snapshot.getValue(DailyTotals.class);
            dailyTotals.addPreviousPayment(bill);
            dailyTotals.addDailyTotalItems(bill);

            dailyTotalsDb.child("previousPayments").setValue(dailyTotals.getPreviousPayments());
            dailyTotalsDb.child("totals").setValue(dailyTotals.getTotals());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("BillViewFragment", error.getMessage());
          }
        });
      }
    });

    return view;
  }

  public static BillViewFragment newInstance() {
    return new BillViewFragment();
  }

  public static BillViewFragment newInstance (String qrResult) {
    BillViewFragment billView = new BillViewFragment();
    Bundle args = new Bundle();
    args.putString("qrResult", qrResult);
    billView.setArguments(args);
    return billView;
  }
}
