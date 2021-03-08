package com.example.tabdab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfoFragment extends Fragment {

  Button saveInfoBut;
  EditText newFirstName, newLastName, newUserEmail, vendorId, newCardNum, newExpDate, newCVV;
  Switch switchIsVendor;
  FirebaseAuth fireAuth;
  DatabaseReference database;
  FirebaseUser user;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_info, container, false);

    saveInfoBut = view.findViewById(R.id.saveEditBut);
    newFirstName = view.findViewById(R.id.editFirstName);
    newLastName = view.findViewById(R.id.editLastName);
    newUserEmail = view.findViewById(R.id.editEmailtxt);
    vendorId = view.findViewById(R.id.vendorId);
    switchIsVendor = view.findViewById(R.id.isVendor);
    newCardNum = view.findViewById(R.id.editCardNum);
    newExpDate = view.findViewById(R.id.editExpDate);
    newCVV = view.findViewById(R.id.editCVV);


    // Vendor switch is switched to true, make the vendor ID input visible to the user
    switchIsVendor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (switchIsVendor.isChecked()) {
          vendorId.setVisibility(View.VISIBLE);
        } else {
          vendorId.setVisibility(View.INVISIBLE);
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
        String newEmail = newUserEmail.getText().toString().trim();
        String newCard = newCardNum.getText().toString();
        String newDate = newExpDate.getText().toString();
        String newCV = newCVV.getText().toString();
        final String vendorID = vendorId.getText().toString();

        // Check if the vendor ID the user entered exists.
       // if(vendorId.getVisibility() == View.VISIBLE()) {
          DatabaseReference refVendors = FirebaseDatabase.getInstance().getReference().child("vendors");
          final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
          refVendors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (!snapshot.hasChild(vendorID) || vendorID.isEmpty()) {  // Vendor ID not found
                System.out.println(vendorID);
                Toast.makeText(getContext(), "Vendor ID not found.", Toast.LENGTH_SHORT).show();
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
            user = fireAuth.getCurrentUser();
        // Check which fields the user has updated and update them
        if (!firstName.isEmpty()) {
          refUser.child("firstName").setValue(firstName);
        } else if (!lastName.isEmpty()) {
          refUser.child("lastName").setValue(lastName);
        } else if (!newEmail.isEmpty()) {
          refUser.child("email").setValue(newEmail);
          user.updateEmail(newEmail);
        } else if (!newCard.isEmpty()) {
          refUser.child("cardNum").setValue(newCard);
        } else if (!newDate.isEmpty()) {
          refUser.child("expDate").setValue(newDate);
        } else if (!newCV.isEmpty()) {
          refUser.child("cvv").setValue(newCV);
        }

      }
    });

    return view;
  }

  public EditInfoFragment() {
    // Required empty public constructor
  }

  public static EditInfoFragment newInstance() {
    return new EditInfoFragment();
  }
}