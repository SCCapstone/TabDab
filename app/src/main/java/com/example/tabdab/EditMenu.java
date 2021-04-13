package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditMenu extends AppCompatActivity {
  private EditText editName, editPrice;
  private Button ButAddItem, ButRemoveItem, ButCancel, ButDone;
  private ScrollView scroller;
  private LinearLayout menu;

  // Database references
  private DatabaseReference dbVendor, dbUser;
  private FirebaseUser userRef;
  private User user;
  private Vendor vendor;
  private List<BillItem> menuItems;

  MainActivity ma;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_menu);

    String userStr = getIntent().getStringExtra("USER");
    user = User.fromJson(userStr);

    // UI components
    editName = findViewById(R.id.menuItemName);
    editPrice = findViewById(R.id.menuItemPrice);
    ButAddItem = findViewById(R.id.ButAddItem);
    ButRemoveItem = findViewById(R.id.ButRemoveItem);
    ButCancel = findViewById(R.id.ButCancel);
    ButDone = findViewById(R.id.ButDone);
    scroller = findViewById(R.id.scroller);
    menu = findViewById(R.id.menu);
    final Context context = this;

    // Database references
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    dbVendor = FirebaseDatabase.getInstance().getReference("vendors/");
    dbUser = FirebaseDatabase.getInstance().getReference("users/").child(userRef.getUid());

    // Add a menu item on button press
    dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        // Get appropriate user and vendor
        vendor = snapshot.child(user.getVendorID()).getValue(Vendor.class);

        // When the add button is pressed a new item is added to the list in the database
        ButAddItem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (editName.getText().toString().isEmpty()) {
              editName.setError("Cannot have empty name.");
              return;
            }
            if (editPrice.getText().toString().isEmpty()) {
              editPrice.setError("Cannot have empty price.");
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

            // Updated firebase
            vendor.menu.add(new BillItem(Double.parseDouble(editPrice.getText().toString()), editName.getText().toString()));
            FirebaseDatabase.getInstance().getReference().child("vendors").child(vendor.vendorId).setValue(vendor);

            // Clear the edit text boxes
            editName.setText("");
            editPrice.setText("");
          }
        });
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("EditMenu.java", error.getMessage());
      }
    });

    // Set the text view to the menu stored in firebase
    dbVendor.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull final DataSnapshot snapshot) {
        menu = findViewById(R.id.menu);
        menu.removeAllViews();
        menuItems = vendor.getMenu();

        // OnClickListener to remove an item from the menu
        View.OnClickListener listener = new View.OnClickListener() {
          @Override
          public void onClick (View v) {
            // Remove the menu item selected and send the new menu to firebase
            menuItems.remove(v.getId());
            vendor.setMenu(menuItems);
            FirebaseDatabase.getInstance().getReference().child("vendors").child(vendor.vendorId).setValue(vendor);

            // Set remove and cancel button visibility
            ButCancel.setVisibility(View.INVISIBLE);
            ButRemoveItem.setVisibility(View.VISIBLE);
          }
        };

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          Button but = new Button(context);
          but.setId(i);
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          params.setMargins( 0, 0, 0, 5);
          but.setLayoutParams(params);
          but.setText(menuItems.get(i).getName() + ": $" + menuItems.get(i).getPrice());
          but.setEnabled(false);
          but.setAlpha(.5f);
          but.setBackground(getDrawable(R.drawable.register_button));
          but.setTextColor(Color.WHITE);
          but.setAllCaps(false);
          but.setOnClickListener(listener);
          menu.addView(but);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("EditMenu.java", error.getMessage());
      }
    });

    ButCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dbVendor.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            ButCancel.setVisibility(View.INVISIBLE);
            ButRemoveItem.setVisibility(View.VISIBLE);

            // Set all of the buttons in the layout to clickable
            for (int i = 0; i < menu.getChildCount(); i++) {
              Button but = findViewById(i);
              but.setEnabled(false);
              but.setAlpha(.5f);
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("EditMenu.java", "ButCancel onClick");
          }
        });
      }
    });

    ButRemoveItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ButRemoveItem.setVisibility(View.INVISIBLE);
        ButCancel.setVisibility(View.VISIBLE);

        // Set all of the buttons in the layout to clickable
        for (int i = 0; i < menu.getChildCount(); i++) {
          Button but = findViewById(i);
          but.setEnabled(true);
          but.setAlpha(1);
        }
      }
    });

    ButDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
      }
    });
  }

  @Override
  public void onBackPressed() {
    startActivity(new Intent(getApplicationContext(), MainActivity.class));
  }

  public static boolean isNumeric(String str) {
    try {
      double dbl = Double.parseDouble(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}