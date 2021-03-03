package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity{
    TextView LoginEmail, LoginPassword;
    Button ButRegister, ButLogin;
    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check for camera permission
        int requestCode = 0;
        while (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }

        //Setting up variables for all parts on login screen
        LoginEmail = findViewById(R.id.LogEmail);
        LoginPassword = findViewById(R.id.LogPass);
        ButLogin = findViewById(R.id.loginButton);
        ButRegister = findViewById(R.id.registerButton);
        fireAuth = FirebaseAuth.getInstance();

        // Send to registration page if user clicks register buttons
        ButRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateAccount.class));
            }
        });

        //When user attempts to login
        ButLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get what the user entered in the login fields
                String email = LoginEmail.getText().toString().trim();
                String password = LoginPassword.getText().toString().trim();

                //Checks to make sure that information was entered and that it follows
                if(TextUtils.isEmpty(email)) {
                    LoginEmail.setError(getString(R.string.email_required));
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    LoginPassword.setError(getString(R.string.password_required));
                    return;
                }

                //Check to see if user exists already in firebase
                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If user exists they can login, if not they must register or fix typing
                        if(task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), AccountInformation.class));
                        }
                        else {
                            LoginPassword.setError(getString(R.string.invalid_user_error));
                        }
                    }
                });
            }
        });
    }
}
