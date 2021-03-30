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

import org.w3c.dom.Text;

import java.util.List;

public class PastPaymentsFragment extends Fragment {
  ScrollView scroller;
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

    // Database elements
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    database = FirebaseDatabase.getInstance().getReference().child("users").child(userRef.getEmail().replace('.','*'));
    prevPaymentsStr = "";

    // Get the users past payments and add them to the scroll view
    database.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        user = snapshot.getValue(User.class);

        // If the user doesn't have any past payments let them know, otherwise show them
        if (user.getPastPayments().isEmpty() || user.getPastPayments() == null ||
        user.getPastPayments().size() == 1) {  // There is always at least a dummy past payment
          TextView noPastPayments = new TextView(getContext());
          noPastPayments.setText("No previous payments.");
          noPastPayments.setTextColor(Color.WHITE);
          payments.addView(noPastPayments);
        } else {
          List<Bill> prevPayments = user.getPastPayments();

          // Set the text views parameters and add it
          for (int i = prevPayments.size() - 1; i > 0; i--) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            LinearLayout bill = new LinearLayout(getContext());

            TextView date = new TextView(getContext());
            TextView vendorName = new TextView(getContext());
            TextView itemizedBill = new TextView(getContext());
            TextView grandTotal = new TextView(getContext());

            // Set the past payment parameters
            bill.setId(i);
            bill.setOrientation(LinearLayout.VERTICAL);
            bill.setBackground(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.textview_pink, null));
            bill.setPadding(10, 10, 10, 10);
            bill.setLayoutParams(params);

            // Set the text parameters
            date.setLayoutParams(params);
            date.setText(prevPayments.get(i).getDate());
            date.setTextColor(Color.WHITE);
            vendorName.setText(prevPayments.get(i).getVendor());
            vendorName.setTextSize(25);
            vendorName.setTextColor(Color.WHITE);
            vendorName.setLayoutParams(params);
            itemizedBill.setText(prevPayments.get(i).itemizedBillToString());
            itemizedBill.setTextColor(Color.WHITE);
            itemizedBill.setLayoutParams(params);
            grandTotal.setText("Grand Total: " + prevPayments.get(i).getGrandTotal());
            grandTotal.setTextColor(Color.WHITE);
            grandTotal.setLayoutParams(params);

            // Add the text views to the previous payment
            bill.addView(date);
            bill.addView(vendorName);
            bill.addView(itemizedBill);
            bill.addView(grandTotal);

            // Add the previous payment
            payments.addView(bill);
          }
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