package com.example.tabdab;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

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

public class VendorDailyTotalsFragment extends Fragment {
  ScrollView scroller;
  LinearLayout payments;
  String prevPaymentsStr;

  User user;
  Vendor vendor;
  DatabaseReference vendorDb;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // Get the user from the previous fragment and then initialize the bill
      String userStr = getArguments().getString("userStr", "");
      user = User.fromJson(userStr);
    }
    vendorDb = FirebaseDatabase.getInstance().getReference("vendors").child(user.getVendorID());

    // Get the vendor the user belongs to
    vendorDb.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        vendor = snapshot.getValue(Vendor.class);
        List<Bill> prevPayments = vendor.getPreviousPayments();

        // Set the text views parameters and add it
        for (int i = prevPayments.size()-1; i >= 0; i--) {
          TextView pastBill = new TextView(getContext());
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          params.setMargins(0,10,0,10);
          pastBill.setId(i);
          pastBill.setText(prevPayments.get(i).toString());
          pastBill.setTextColor(Color.WHITE);
          pastBill.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.textview_pink, null));
          pastBill.setLayoutParams(params);
          pastBill.setPadding(10,10,10,10);
          payments.addView(pastBill);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_vendor_daily_totals, container, false);

    scroller = view.findViewById(R.id.scroller);
    payments = view.findViewById(R.id.payments);

    return view;
  }

  public VendorDailyTotalsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param user User sent in from the vendor menu fragment
   * @return A new instance of fragment vendor_daily_totals.
   */
  public static VendorDailyTotalsFragment newInstance(User user) {
    VendorDailyTotalsFragment fragment = new VendorDailyTotalsFragment();
    String userStr = user.toJson();
    Bundle args = new Bundle();
    args.putString("userStr", userStr);
    fragment.setArguments(args);
    return fragment;
  }

}