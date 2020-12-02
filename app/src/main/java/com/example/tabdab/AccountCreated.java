package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountCreated extends AppCompatActivity {
    Button butContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);

        butContinue = findViewById(R.id.butContinue);
        butContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountInfo = new Intent(getApplicationContext(), AccountInformation.class);
                startActivity(accountInfo);
            }
        });
    }
}