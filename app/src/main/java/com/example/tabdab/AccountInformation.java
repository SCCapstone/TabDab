package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountInformation extends AppCompatActivity{
    TextView UserName, UserEmail;
    Button ButChangePass, ButEditInfo;
    FirebaseAuth fireAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

      //  UserName = findViewById(R.id.AccountName);
      //  UserEmail = findViewById(R.id.AccountEmail);
        ButChangePass = findViewById(R.id.chngPassBut);
        ButEditInfo = findViewById(R.id.editInfoBut);

        fireAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    String UID = user.getUid();
                    String UEmail = user.getEmail();
                    UserName = (TextView)findViewById(R.id.AccountName);
                    UserName.setText(UID);
                    UserEmail = (TextView)findViewById(R.id.AccountEmail);
                    UserEmail.setText(UEmail);
                }
            }
        };



    }
}
