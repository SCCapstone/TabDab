package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Map;

public class BillViewFragment extends Fragment {
  TextView itemizedView, grandTotalView, editTip;
  Button butAddTip, butPay;
  Bill bill;
  String qrResult;

  DatabaseReference userDb, vendorDb, dailyTotalsDb, paymentsDb, billsDb;
  FirebaseUser userRef;
  User user;
  DailyTotals dailyTotals;
  PaymentTracker paymentTracker;

  MainActivity ma;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();

    // Get the QR code contents from the previous fragment
    qrResult = getArguments().getString("qrResult", "");

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

    // Check that the qr code contained a reference to a tabdab bill
    if (qrResult.contains("https://tabdab-a9e3a.firebaseio.com/")) {
      billsDb = FirebaseDatabase.getInstance().getReferenceFromUrl(qrResult);

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
              // Update the user locally and in the database
              user.addPastPayment(bill);
              ma.mainActSetUser(user);
              userDb.child("pastPayments").setValue(user.getPastPayments());

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

                  // Go back the QR scanner
                  FragmentTransaction ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                          R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                  ft.replace(R.id.fragment_container, QrScannerFragment.newInstance()).commit();
                  ft.addToBackStack(null);
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
          Log.d("BillViewFragment", error.getMessage());
        }
      });
    } else {  // Bill isn't found in the database
      itemizedView.setText("Bill not found. Make sure the QR code is generated from a TabDab vendor.");
      butAddTip.setVisibility(View.INVISIBLE);
      butPay.setVisibility(View.INVISIBLE);
      editTip.setVisibility(View.INVISIBLE);
    }

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

  public static BillViewFragment newInstance (String qrResult) {
    BillViewFragment billView = new BillViewFragment();
    Bundle args = new Bundle();
    args.putString("qrResult", qrResult);
    billView.setArguments(args);
    return billView;
  }
}
