package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditInfo extends AppCompatActivity {
    Button saveInfoBut;
    EditText newUserName, newUserEmail;
    FirebaseAuth fireAuth;
    DatabaseReference database;
    FirebaseUser user;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        Intent currentInfo = getIntent();

        String currentEmail = currentInfo.getStringExtra("Email");
        String currentName = currentInfo.getStringExtra("Name");

        saveInfoBut = (Button) findViewById(R.id.saveEditBut);
        newUserName = (EditText) findViewById(R.id.editNametxt);
        newUserEmail = (EditText) findViewById(R.id.editEmailtxt);

        fireAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance().getReference("users/");
        user = fireAuth.getCurrentUser();

        newUserName.setHint(currentName);
        newUserEmail.setHint(currentEmail);

        saveInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newUserName.getText().toString();
                String newEmail = newUserEmail.getText().toString();
                if(newName.isEmpty() || newEmail.isEmpty()) {
                    Toast.makeText(EditInfo.this,"Please enter what you would like to change", Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO fix this for the new first and last name attributes in user
                    User newUserInfo = new User(newName, newName, newEmail, false);
                    user.updateEmail(newEmail);
                    FirebaseDatabase.getInstance().getReference("users/").child(user.getUid()).setValue(newUserInfo);
                    startActivity(new Intent(getApplicationContext(),AccountInformation.class));
                }




            }
        });


    }
}
