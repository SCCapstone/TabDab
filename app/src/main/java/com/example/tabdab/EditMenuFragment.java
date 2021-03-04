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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EditMenuFragment extends Fragment {

  // the fragment initialization parameters
  static final String USER_STR = "userStr";

  // UI components initialization
  EditText editName, editPrice;
  Button butAddItem, butRemoveItem, butCancel;
  ScrollView scroller;
  LinearLayout menu;

  // Database references
  DatabaseReference dbVendor;
  User user;
  Vendor vendor;
  List<BillItem> menuItems;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      String userStr = getArguments().getString(USER_STR, "");
      user = User.fromJson(userStr);

      // Database references
      dbVendor = FirebaseDatabase.getInstance().getReference("vendors").child(user.getVendorID());
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);
    editName = view.findViewById(R.id.menuItemName);
    editPrice = view.findViewById(R.id.menuItemPrice);
    butAddItem = view.findViewById(R.id.ButAddItem);
    butRemoveItem = view.findViewById(R.id.ButRemoveItem);
    butCancel = view.findViewById(R.id.ButCancel);
    scroller = view.findViewById(R.id.scroller);
    menu = view.findViewById(R.id.menu);

    // Get the menu from firebase so the user can see it. Update it when items are added/removed
    dbVendor.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        vendor = snapshot.getValue(Vendor.class);
        menu.removeAllViews();  // Reset the menu view after an item is added/removed
        menuItems = vendor.getMenu();

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          Button but = new Button(getContext());
          but.setId(i);
          but.setText(menuItems.get(i).getName() + ": $" + menuItems.get(i).getPrice());
          but.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.register_button, null));
          but.setTextColor(Color.WHITE);

          // Set the on click listener
          but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
              // Remove the menu item selected and send the new menu to firebase
              menuItems.remove(v.getId());
              vendor.setMenu(menuItems);
              FirebaseDatabase.getInstance().getReference().child("vendors").child(vendor.vendorId).setValue(vendor);

              // Set remove and cancel button visibility
              butCancel.setVisibility(View.INVISIBLE);
              butRemoveItem.setVisibility(View.VISIBLE);
            }
          });
          menu.addView(but);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("CreateBillFragment.java", error.getMessage());
      }
    });

    // Add a menu item on button press
    dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        // Get appropriate user and vendor
        vendor = snapshot.getValue(Vendor.class);

        // When the add button is pressed a new item is added to the list in the database
        butAddItem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // Check what the user enters is valid
            if (editName.getText().toString().isEmpty()) {
              editName.setError("Cannot have empty name.");
              return;
            }
            if (editPrice.getText().toString().isEmpty()) {
              editPrice.setError("Cannot have empty name.");
              return;
            }
            if (!isNumeric(editPrice.getText().toString())) {
              editPrice.setError("Must be a number");
              return;
            }
            if (editName.getText().toString().contains(":") ||
                    editName.getText().toString().contains(",") ||
                    editName.getText().toString().contains("$")) {
              editName.setError("Cannot contain special characters.");
              return;
            }

            // Update firebase
            vendor.menu.add(new BillItem(Double.parseDouble(editPrice.getText().toString()), editName.getText().toString()));
            FirebaseDatabase.getInstance().getReference().child("vendors").child(vendor.vendorId).setValue(vendor);
          }
        });
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("EditMenu.java", error.getMessage());
      }
    });

    /*
    butRemoveItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        butRemoveItem.setVisibility(View.INVISIBLE);
        butCancel.setVisibility(View.VISIBLE);

        // Set all of the buttons in the layout to clickable
        for (int i = 0; i < menu.getChildCount(); i++) {
          Button but = view.findViewById(i);
          but.setEnabled(true);
        }
      }
    });
    */

    return view;
  }

  /**
   * @User user from the vendor menu class for faster loading of the UI
   * @return A new instance of fragment EditMenuFragment.
   */
  public static EditMenuFragment newInstance(User user) {
    EditMenuFragment editMenuFragment = new EditMenuFragment();
    String userStr = user.toJson();
    Bundle args = new Bundle();
    args.putString("userStr", userStr);
    editMenuFragment.setArguments(args);
    return editMenuFragment;
  }

  public EditMenuFragment() {
    // Required empty public constructor
  }

  /**
   * Checks that what the user entered into the text box is a number
   * @param str String entered in the text box
   * @return whether it was or was not a number
   */
  public static boolean isNumeric(String str) {
    try {
      double dbl = Double.parseDouble(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}