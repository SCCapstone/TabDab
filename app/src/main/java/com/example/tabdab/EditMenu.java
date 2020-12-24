package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditMenu extends AppCompatActivity {
    EditText editName, editPrice;
    Button ButAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        editName = findViewById(R.id.menuItemName);
        editPrice = findViewById(R.id.menuItemPrice);
        ButAddItem = findViewById(R.id.ButAddItem);
    }

    // TODO add the dynamic code for adding buttons
}