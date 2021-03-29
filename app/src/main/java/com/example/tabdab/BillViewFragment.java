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

  DatabaseReference userDb, vendorDb, dailyTotalsDb, paymentsDb, billsDb;
  FirebaseUser userRef;
  User user;
  DailyTotals dailyTotals;
  PaymentTracker paymentTracker;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    String qrResult = getArguments().getString("qrResult", "");
    billsDb = FirebaseDatabase.getInstance().getReferenceFromUrl(qrResult);

    String userStr = getArguments().getString("user", "");
    user = User.fromJson(userStr);
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userDb = FirebaseDatabase.getInstance().getReference().child("users").child(user.getEmail().replace('.', '*'));
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
    billsDb.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        bill = snapshot.getValue(Bill.class);

        vendorDb = FirebaseDatabase.getInstance().getReference("vendors").child(bill.getVendorId());

        // Set UI components
        itemizedView.setText(bill.toString());
        grandTotalView.setText(Double.toString(bill.getGrandTotal()));

        butPay.setOnClickListener(new View.OnClickListener() {
          // Update the users past payments
          @Override
          public void onClick (View view) {
            List<Bill> pastPayments = user.getPastPayments();
            pastPayments.add(bill);
            userDb.child("pastPayments").setValue(pastPayments);

            // Track the payment
            paymentTracker = new PaymentTracker(user.getFirstName() + " " + user.getLastName(),
                    bill.getVendor(), bill.getGrandTotal(), bill.getTip());
            paymentsDb = FirebaseDatabase.getInstance().getReference("payments").push();
            paymentsDb.setValue(paymentTracker);

            Toast.makeText(getContext(), "Bill Payed!", Toast.LENGTH_SHORT).show();

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
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    // Add tip
    butAddTip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View view) {
        bill.setTip(Double.parseDouble(editTip.getText().toString()));
        grandTotalView.setText(Double.toString(bill.getGrandTotal() + bill.getTip()));
        itemizedView.setText(bill.toString());
      }
    });

    return view;
  }

  public static BillViewFragment newInstance() {
    return new BillViewFragment();
  }

  public static BillViewFragment newInstance (User user, String qrResult) {
    BillViewFragment billView = new BillViewFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    args.putString("qrResult", qrResult);
    billView.setArguments(args);
    return billView;
  }
}
