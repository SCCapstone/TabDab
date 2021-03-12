package com.example.tabdab;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CreateBillFragment extends Fragment {
  // Set up ui elements
  ScrollView scroller, itemizedBillScroller;
  LinearLayout itemized_bill_layout;
  Button generateBtn, clearBtn;
  TextView itemizedBill;
  LinearLayout menu;

  // User elements
  String userId;
  DatabaseReference databaseVendor;
  FirebaseUser userRef;
  User user;
  Vendor vendor;
  Bill bill;
  List<BillItem> menuItems;
  String itemizedBillStr;

  public CreateBillFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Get the user from the previous fragment and then initialize the bill
    String userStr = getArguments().getString("userStr", "");
    user = User.fromJson(userStr);
    bill = new Bill();
    itemizedBillStr = "";

    // Database references
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userId = userRef.getUid();
  }

  @Override
  public void onResume () {
    super.onResume();
    bill = new Bill();
    itemizedBillStr = "";
    itemizedBill.setText(itemizedBillStr);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_create_bill, container, false);
    generateBtn = view.findViewById(R.id.generate_bill_btn);
    clearBtn = view.findViewById(R.id.clear_btn);
    itemized_bill_layout = view.findViewById(R.id.itemized_bill_layout);
    itemizedBill = view.findViewById(R.id.itemized_bill);
    scroller = view.findViewById(R.id.scroller);
    itemizedBillScroller = view.findViewById(R.id.itemized_bill_scroller);
    menu = view.findViewById(R.id.menu);

    // Get the vendor information
    databaseVendor = FirebaseDatabase.getInstance().getReference("vendors").child(user.getVendorID());
    databaseVendor.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        vendor = snapshot.getValue(Vendor.class);
        menuItems = vendor.getMenu();

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          Button but = new Button(getContext());
          but.setId(i);
          but.setText(menuItems.get(i).getName() + ": $" + menuItems.get(i).getPrice());
          but.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.register_button, null));
          but.setTextColor(Color.WHITE);
          but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
              bill.addBillItem(menuItems.get(v.getId()));

              // Update the itemized bill
              if (itemizedBillStr.isEmpty()) {
                itemizedBillStr = menuItems.get(v.getId()).getName();
              } else {
                itemizedBillStr = itemizedBillStr + ", " + menuItems.get(v.getId()).getName();
              }
              itemizedBill.setText(itemizedBillStr);
            }
          });
          menu.addView(but);
        }

        // Set the generate button to launch the bill show fragment.
        generateBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            bill.setVendor(vendor.getName());
            bill.setVendorId(vendor.getVendorId());

            FragmentTransaction ft;
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, BillShowFragment.newInstance(bill.toJson())).commit();
            ft.addToBackStack(null);
          }
        });

        // Clear the bill if the user needs to restart a bill
        clearBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            itemizedBillStr = "";
            itemizedBill.setText(itemizedBillStr);
            bill = new Bill();
          }
        });
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("CreateBillFragment.java", error.getMessage());
      }
    });

    return view;
  }

  public static CreateBillFragment newInstance () {return new CreateBillFragment();}

  public static CreateBillFragment newInstance (User user) {
    CreateBillFragment createBillFragment = new CreateBillFragment();
    String userStr = user.toJson();
    Bundle args = new Bundle();
    args.putString("userStr", userStr);
    createBillFragment.setArguments(args);
    return createBillFragment;
  }
}