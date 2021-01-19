package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateVendor extends AppCompatActivity {
  TextView vendorName, loginPassword, loginEmail;
  Button register;
  DatabaseReference vendors;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_vendor);

    vendorName = findViewById(R.id.editVendorName);
    register = findViewById(R.id.butRegisterVendor);

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View view) {
        makeVendor(vendorName.getText().toString().trim());
      }
    });
  }

  /**
   * Generates a unique vendorID and stores the vendor name inside of the database
   * @param vendorName Name of the vendor
   */
  public void makeVendor (String vendorName) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    vendors = FirebaseDatabase.getInstance().getReference("vendors").push();
    List<BillItem> menu = new ArrayList<>();
    //menu.add(new BillItem(1, "testobject"));
    Vendor vendor = new Vendor(vendors.getKey(), vendorName, menu);
    vendors.setValue(vendor);

    // Update data in firebase.
    FirebaseDatabase.getInstance().getReference("users/").child(uid).child("vendorID").setValue(vendors.getKey());
    FirebaseDatabase.getInstance().getReference("users/").child(uid).child("isVendor").setValue(true);
  }
}