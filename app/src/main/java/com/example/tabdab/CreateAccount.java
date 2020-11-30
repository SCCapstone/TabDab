package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText uFirstName,uLastName,uPassword,uEmail;
    private Button uRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        uEmail = findViewById(R.id.uEmail);
        uFirstName = findViewById(R.id.uFirstName);
        uLastName = findViewById(R.id.uLastName);
        uPassword = findViewById(R.id.uPassword);
        uRegister = findViewById(R.id.btnRegister);


        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        uRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString().trim();
                final String firstName = uFirstName.getText().toString().trim();
                final String lastName = uLastName.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    uEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    uPassword.setError("Password is required");
                    return;
                }
                if(password.length()<8){
                    uPassword.setError("Password must be at least 8 characters");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User((firstName + lastName), email);
                            FirebaseDatabase.getInstance().getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreateAccount.this, "User Created.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AccountCreated.class));
                                    } else {
                                        Toast.makeText(CreateAccount.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

    }
}