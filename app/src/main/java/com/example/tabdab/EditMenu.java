package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editName, editPrice;
    TextView menuView;
    Button ButAddItem;
    String userId;
    DatabaseReference database;
    FirebaseUser userRef;
    User user;
    Vendor vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        editName = findViewById(R.id.menuItemName);
        editPrice = findViewById(R.id.menuItemPrice);
        ButAddItem = findViewById(R.id.ButAddItem);
        menuView = findViewById(R.id.menuView);

        userRef = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        userId = userRef.getUid();

        // Add a menu item on button press
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get appropriate user and vendor
                user = snapshot.child("users").child(userId).getValue(User.class);
                vendor = snapshot.child("vendors").child(user.getVendorID()).getValue(Vendor.class);

                // When the add button is pressed a new item is added to the list in the database
                ButAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

        // Set the text view to the menu stored in firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child("users").child(userId).getValue(User.class);
                vendor = snapshot.child("vendors").child(user.getVendorID()).getValue(Vendor.class);
                menuView.setText(vendor.menuToString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EditMenu.java", error.getMessage());
            }
        });
    }
}