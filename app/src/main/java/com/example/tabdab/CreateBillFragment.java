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
  private ScrollView scroller, itemizedBillScroller;
  private LinearLayout itemized_bill_layout;
  private Button generateBtn, clearBtn;
  private TextView itemizedBill;
  private LinearLayout menu;

  // User elements
  private String userId;
  private DatabaseReference vendorsDb, billsDb;
  private FirebaseUser userRef;
  private User user;
  private Vendor vendor;
  Bill bill;
  private List<BillItem> menuItems;
  private String itemizedBillStr;

  MainActivity ma;

  public CreateBillFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();

    // Get the user from the previous fragment and then initialize the bill
    user = ma.mainActGetUser();
    bill = new Bill();
    itemizedBillStr = "";

    // Database references
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userId = userRef.getUid();
  }

  // Reset the bill values when the user comes back to this screen
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
    vendorsDb = FirebaseDatabase.getInstance().getReference("vendors").child(user.getVendorID());
    vendorsDb.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        vendor = snapshot.getValue(Vendor.class);
        menuItems = vendor.getMenu();

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          Button but = new Button(getContext());
          but.setId(i);
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          params.setMargins( 0, 0, 0, 5);
          but.setLayoutParams(params);
          but.setText(menuItems.get(i).getName() + ": $" + String.format("%.2f", menuItems.get(i).getPrice()));
          but.setAllCaps(false);
          but.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pink_white_outline, null));
          but.setTextColor(Color.WHITE);
          but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
              bill.addBillItem(menuItems.get(v.getId()));
              bill.setGrandTotal();

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

            billsDb = FirebaseDatabase.getInstance().getReference("bills").push();
            billsDb.setValue(bill);

            billsDb = FirebaseDatabase.getInstance().getReference("bills").child(billsDb.getKey());

            FragmentTransaction ft;
            ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            ft.replace(R.id.fragment_container, BillShowFragment.newInstance(billsDb.toString())).commit();
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