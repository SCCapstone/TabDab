package com.example.tabdab;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {
  Button saveInfoBut;
  EditText newFirstName, newLastName, newUserEmail, newVendorID, newCardNum, newExpDate, newCVV;
  Switch switchIsVendor;
  FirebaseAuth fireAuth;
  DatabaseReference database;
  FirebaseUser user;

  @Override
  protected void onCreate( Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_information);

    saveInfoBut = findViewById(R.id.saveEditBut);
    newFirstName = findViewById(R.id.editFirstName);
    newLastName = findViewById(R.id.editLastName);
    newUserEmail = findViewById(R.id.editEmailtxt);
    newVendorID = findViewById(R.id.vendorId);
    switchIsVendor = findViewById(R.id.isVendor);
    newCardNum = findViewById(R.id.editCardNum);
    newExpDate = findViewById(R.id.editExpDate);
    newCVV = findViewById(R.id.editCVV);


    // Vendor switch is switched to true, make the vendor ID input visible to the user
    switchIsVendor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (switchIsVendor.isChecked()) {
          newVendorID.setVisibility(View.VISIBLE);
        } else {
          newVendorID.setVisibility(View.INVISIBLE);
        }
      }
    });

    fireAuth = FirebaseAuth.getInstance();
    database =  FirebaseDatabase.getInstance().getReference("users/");
    user = fireAuth.getCurrentUser();

    saveInfoBut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String firstName = newFirstName.getText().toString();
        String lastName = newLastName.getText().toString();
        String newEmail = newUserEmail.getText().toString();
        String newCard = newCardNum.getText().toString();
        String newDate = newExpDate.getText().toString();
        String newCV = newCVV.getText().toString();
        final String vendorID = newVendorID.getText().toString();

        // Check if the vendor ID the user entered exists.
        DatabaseReference refVendors = FirebaseDatabase.getInstance().getReference().child("vendors");
        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refVendors.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(!snapshot.hasChild(vendorID) || vendorID.isEmpty()) {  // Vendor ID not found
              System.out.println(vendorID);
              Toast.makeText(EditInfo.this, "Vendor ID not found.", Toast.LENGTH_SHORT).show();
            } else if (!vendorID.isEmpty()) {  // Vendor ID exists. Update the user info
              refUser.child("vendorID").setValue(vendorID);
              refUser.child("isVendor").setValue(true);
            }
          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("EditInfo.java", error.getMessage());
          }
        });

        // Check which fields the user has updated and update them
        if (!firstName.isEmpty()) {
          refUser.child("firstName").setValue(firstName);
        } else if (!lastName.isEmpty()) {
          refUser.child("lastName").setValue(lastName);
        } else if (!newEmail.isEmpty()) {
          refUser.child("email").setValue(newEmail);
        } else if (!newCard.isEmpty()) {
          refUser.child("cardNum").setValue(newCard);
        } else if (!newDate.isEmpty()) {
          refUser.child("expDate").setValue(newDate);
        } else if (!newCV.isEmpty()) {
          refUser.child("cvv").setValue(newCV);
        }

        Toast.makeText(getApplicationContext(), "Account updated!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), AccountInformation.class));
      }
    });


  }
}
