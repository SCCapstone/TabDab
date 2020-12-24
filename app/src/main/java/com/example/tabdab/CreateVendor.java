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
    Vendor vendor = new Vendor(vendorName);
    FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    FirebaseUser user = fireAuth.getCurrentUser();

    vendors = FirebaseDatabase.getInstance().getReference("vendors").push();
    vendors.child("name").setValue(vendorName);
    vendor.setVendorId(vendors.getKey());

    // Update data in firebase.
    FirebaseDatabase.getInstance().getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("vendorID").setValue(vendors.getKey());
    FirebaseDatabase.getInstance().getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("isVendor").setValue(true);
  }
}