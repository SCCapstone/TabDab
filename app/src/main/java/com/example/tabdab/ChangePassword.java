package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassword extends AppCompatActivity {
    private EditText userEmail;
    private Button resetPassBut, goBackBut;
    FirebaseAuth fireAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userEmail = (EditText) findViewById(R.id.chngPassEmail);
        resetPassBut = (Button) findViewById(R.id.sndEmailBut);
        goBackBut = (Button) findViewById(R.id.backBut);

        fireAuth = FirebaseAuth.getInstance();

        resetPassBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    userEmail.setError("Please enter your email address.");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    userEmail.setError("This email does not belong to any account, please enter a different email address.");
                } else {
                    fireAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePassword.this, "An email has been sent to reset your password.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(ChangePassword.this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        goBackBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(getApplicationContext(), AccountInformation.class);
                startActivity(goBack);
            }
        });

    }
}
