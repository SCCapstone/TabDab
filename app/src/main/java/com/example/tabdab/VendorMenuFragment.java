package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorMenuFragment extends Fragment {
  private TextView vendorName, vendorId;
  private Button butCreateBill, butEditMenu, butDailyTotals, butEditVendorInfo;
  User user;
  MainActivity ma;

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_vendor_menu, container, false);
    vendorName = view.findViewById(R.id.vendorName);
    vendorId = view.findViewById(R.id.vendorId);
    butCreateBill = view.findViewById(R.id.butMakeBill);
    butEditMenu = view.findViewById(R.id.butEditMenu);
    butDailyTotals = view.findViewById(R.id.butDailyTotals);
    butEditVendorInfo = view.findViewById(R.id.butEditVendor);

    // Display the users vendor ID if they are registered to one
    if (user.getIsVendor()) {
      vendorId.setText("Vendor ID: " + user.getVendorID());
    }

    // Start the new fragments with the user (used to make getting vendor info easier) info on a click
    butCreateBill.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        // Only let the user go if they are a registered vendor
        if (!user.getIsVendor()) {
          Toast.makeText(getContext(), "Please register as a vendor in settings.", Toast.LENGTH_SHORT).show();
        } else {
          FragmentTransaction ft;
          ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                  R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
          ft.replace(R.id.fragment_container, CreateBillFragment.newInstance()).commit();
          ft.addToBackStack(null);
        }
      }
    });

    butEditMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Only let the user go if they are a registered vendor
        if (!user.getIsVendor()) {
          Toast.makeText(getContext(), "Please register as a vendor in settings.", Toast.LENGTH_SHORT).show();
        } else {
          Intent intent = new Intent(getContext(), EditMenu.class);
          intent.putExtra("USER", user.toJson());
          startActivity(intent);
        }
      }
    });

    butDailyTotals.setOnClickListener(new View.OnClickListener () {
      @Override
      public void onClick(View v) {
        // Only let the user go if they are a registered vendor
        if (!user.getIsVendor()) {
          Toast.makeText(getContext(), "Please register as a vendor in settings.", Toast.LENGTH_SHORT).show();
        } else {
          FragmentTransaction ft;
          ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                  R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
          ft.replace(R.id.fragment_container, VendorDailyTotalsFragment.newInstance()).commit();
          ft.addToBackStack(null);
        }
      }
    });

    butEditVendorInfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Only let the user go if they are a registered vendor
        if (!user.getIsVendor()) {
          Toast.makeText(getContext(), "Please register as a vendor in settings.", Toast.LENGTH_SHORT).show();
        } else {
          FragmentTransaction ft;
          ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                  R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
          ft.replace(R.id.fragment_container, EditVendorInfoFragment.newInstance()).commit();
          ft.addToBackStack(null);
        }
      }
    });

    return view;
  }

  public static VendorMenuFragment newInstance () {return new VendorMenuFragment();}
  public static VendorMenuFragment newInstance (User user) {
    VendorMenuFragment vendorMenuFragment = new VendorMenuFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    vendorMenuFragment.setArguments(args);
    return vendorMenuFragment;
  }

  public VendorMenuFragment() {
    // Required empty public constructor
  }
}