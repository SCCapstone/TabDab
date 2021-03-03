package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class CreateBill extends AppCompatActivity {
  // Set up ui elements
  ScrollView scroller, itemizedBillScroller;
  LinearLayout itemized_bill_layout;
  Button generateBtn, clearBtn;
  TextView itemizedBill;

  // Set up class helper info
  public static final String EXTRA_MESSAGE = "com.example.android.tabdab.extra.MESSAGE";
  String userId;
  DatabaseReference database;
  FirebaseUser userRef;
  User user;
  Vendor vendor;
  Bill bill;
  String itemizedBillStr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_bill);

    // Set up the UI layout
    generateBtn = findViewById(R.id.generate_bill_btn);
    clearBtn = findViewById(R.id.clear_btn);
    itemized_bill_layout = findViewById(R.id.itemized_bill_layout);
    itemizedBill = findViewById(R.id.itemized_bill);
    scroller = findViewById(R.id.scroller);
    itemizedBillScroller = findViewById(R.id.itemized_bill_scroller);

    bill = new Bill();
    itemizedBillStr = "";

    // Database references
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    database = FirebaseDatabase.getInstance().getReference();
    userId = userRef.getUid();
    final Context context = this;
    final Intent intent = new Intent(this, BillShow.class);

    // Set scroll view buttons
    database.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        LinearLayout menu = findViewById(R.id.menu);
        user = snapshot.child("users").child(userId).getValue(User.class);
        vendor = snapshot.child("vendors").child(user.getVendorID()).getValue(Vendor.class);
        final List<BillItem> menuItems  = vendor.getMenu();

        // Create an onClickListener that all buttons can use quickly.
        View.OnClickListener listener = new View.OnClickListener() {
          @Override
          public void onClick (View v) {
            bill.addBillItem(menuItems.get(v.getId()));

            // Update the itemized bill at the top of the activity
            if (itemizedBillStr.isEmpty()) {
              itemizedBillStr = menuItems.get(v.getId()).getName();
            } else {
              itemizedBillStr = itemizedBillStr + ", " + menuItems.get(v.getId()).getName();
            }
            itemizedBill.setText(itemizedBillStr);
          }
        };

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
          Button but = new Button(context);
          but.setId(i);
          but.setText(menuItems.get(i).getName() + ": $" + menuItems.get(i).getPrice());
          but.setBackground(getDrawable(R.drawable.register_button));
          but.setTextColor(Color.WHITE);
          but.setOnClickListener(listener);
          menu.addView(but);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("EditMenu.java", error.getMessage());
      }
    });

    // Generate the QR code when the generate button is pressed
    generateBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        bill.setGrandTotal();
        intent.putExtra(EXTRA_MESSAGE, bill.toJson());
        startActivity(intent);
      }
    });
    clearBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        bill = new Bill();
        itemizedBill.setText("");
      }
    });
  }

  public void onBackPressed () {
    startActivity(new Intent(getApplicationContext(), AccountInformation.class));
  }
}