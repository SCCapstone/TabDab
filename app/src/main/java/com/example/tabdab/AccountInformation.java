package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class AccountInformation extends AppCompatActivity{
    TextView UserName, UserEmail;
    Button ButChangePass, ButEditInfo, ButScan, ButGen, ButEditMenu;
    String UID;
    FirebaseAuth fireAuth;
    DatabaseReference database;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        ButChangePass = findViewById(R.id.chngPassBut);
        ButEditInfo = findViewById(R.id.editInfoBut);
        ButScan = findViewById(R.id.scanBut);
        ButGen = findViewById(R.id.ButGen);
        ButEditMenu = findViewById(R.id.ButEditMenu);

        fireAuth = FirebaseAuth.getInstance();
        user = fireAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("users/");
        if(user != null) {
            UID = user.getUid();
            String UEmail = user.getEmail();
            UserName = findViewById(R.id.AccountName);
            UserEmail = findViewById(R.id.AccountEmail);
            database.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userInfo = snapshot.getValue(User.class);
                    if(userInfo != null) {
                        String Name = userInfo.name;
                        String Email = userInfo.email;
                        UserName.setText(Name);
                        UserEmail.setText(Email);
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
            ButGen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    startActivity(new Intent(getApplicationContext(), BillCreator.class));
                }
            });
            ButEditMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), EditMenu.class));
                }
            });
        }
    }
}

