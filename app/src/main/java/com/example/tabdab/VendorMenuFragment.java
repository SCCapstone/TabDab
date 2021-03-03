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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VendorMenuFragment extends Fragment {
  TextView vendorName;
  Button butCreateBill, butEditMenu;
  User user;
  FirebaseUser userRef;
  DatabaseReference userDatabase;

  public VendorMenuFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_vendor_menu, container, false);
    vendorName = view.findViewById(R.id.vendorName);
    butCreateBill = view.findViewById(R.id.butMakeBill);
    butEditMenu = view.findViewById(R.id.butEditMenu);

    // Get the user info
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userDatabase = FirebaseDatabase.getInstance().getReference("users").child(userRef.getUid());
    userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        user = snapshot.getValue(User.class);

        // Start the new fragments with the user (used to make getting vendor info easier) info on a click
        butCreateBill.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick (View v) {
            FragmentTransaction ft;
            ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, CreateBillFragment.newInstance(user)).commit();
            ft.addToBackStack(null);
          }
        });

        butEditMenu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(v.getContext(), EditMenu.class));
          }
        });
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("VendorMenuFragment.java", error.getMessage());
      }
    });

    return view;
  }

  public static VendorMenuFragment newInstance () {return new VendorMenuFragment();}


}