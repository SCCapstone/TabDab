package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInformation extends AppCompatActivity{
    TextView UserName, UserEmail, VendorID, textVendorID;
    Button ButChangePass, ButEditInfo, ButScan, ButGen, ButEditMenu, ButRegisterVendor, butLogout,
            ButPreviousPayments;
    String UID;
    FirebaseAuth fireAuth;
    DatabaseReference database;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        ButChangePass = findViewById(R.id.chngPassBut);
        ButPreviousPayments = findViewById(R.id.ButPreviousPayments);
        ButEditInfo = findViewById(R.id.editInfoBut);
        ButScan = findViewById(R.id.scanBut);
        ButGen = findViewById(R.id.ButGen);
        ButEditMenu = findViewById(R.id.ButEditMenu);
        ButRegisterVendor = findViewById(R.id.registerVendorButton);
        butLogout = findViewById(R.id.butLogout);

        fireAuth = FirebaseAuth.getInstance();
        user = fireAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("users/");

        if(user != null) {
            UID = user.getUid();
            final String UEmail = user.getEmail();  // TODO Possibly remove this (Doesn't get used).
            UserName = findViewById(R.id.AccountName);
            UserEmail = findViewById(R.id.AccountEmail);
            textVendorID = findViewById(R.id.textVendorID);
            VendorID = findViewById(R.id.AccountVendorID);

            database.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userInfo = snapshot.getValue(User.class);
                    if(userInfo != null) {
                        UserName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
                        UserEmail.setText(UEmail);

                        if (userInfo.isVendor) {
                            VendorID.setText(userInfo.vendorID);
                            textVendorID.setVisibility(View.VISIBLE);
                            VendorID.setVisibility(View.VISIBLE);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AccountInformation.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });

            ButChangePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent changePass = new Intent(getApplicationContext(),ChangePassword.class);
                    startActivity(changePass);
                }
            });
            ButPreviousPayments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    startActivity(new Intent(getApplicationContext(), PastPaymentsFragment.class));
                }
            });
            ButEditInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editAcctInfo = new Intent(getApplicationContext(),EditInfo.class);
                    editAcctInfo.putExtra("Name", UserName.getText().toString());
                    editAcctInfo.putExtra("Email", UserEmail.getText().toString());
                    startActivity(editAcctInfo);
                }
            });
            ButScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), QrScannerFragment.class));
                }
            });
            ButGen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    startActivity(new Intent(getApplicationContext(), CreateBill.class));
                }
            });
            ButEditMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), EditMenu.class));
                }
            });
            ButRegisterVendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    startActivity(new Intent(getApplicationContext(), CreateVendor.class));
                }
            });
            butLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //User's tag of LOGIN is removed so that if the app is closed they will not be remembered now
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("LOGIN");
                    editor.commit();

                    fireAuth.signOut();
                    startActivity( new Intent(getApplicationContext(), Login.class));
                }
            });

        }
    }
}

