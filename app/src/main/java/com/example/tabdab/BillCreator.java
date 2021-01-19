package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class BillCreator extends AppCompatActivity {

  // TODO Create a "bill" class to handle qr code data, getGrandTotal, toQRCode, etc.

  // Set up ui elements
  ScrollView scroller;
  Button generateBtn;
  ImageView qrImage;
  TextView itemizedBill;
  String qrValue;

  String userId;
  DatabaseReference database;
  FirebaseUser userRef;
  User user;
  Vendor vendor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bill_creator);

    // Set up the UI layout
    generateBtn = findViewById(R.id.generate_bill_btn);
    qrImage = findViewById(R.id.qrPlaceHolder);
    itemizedBill = findViewById(R.id.itemized_bill);
    scroller = findViewById(R.id.scroller);
    qrValue = "";

    userRef = FirebaseAuth.getInstance().getCurrentUser();
    database = FirebaseDatabase.getInstance().getReference();
    userId = userRef.getUid();
    final Context context = this;

    // Set scroll view buttons
    database.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {

        // Create an onClickListener that all buttons can use quickly.
        View.OnClickListener listener = new View.OnClickListener() {
          @Override
          public void onClick (View v) {
            qrValue = setQRValue(((Button)v).getText().toString());  // Downcast view to a button
          }
        };

        LinearLayout menu = findViewById(R.id.menu);
        user = snapshot.child("users").child(userId).getValue(User.class);
        vendor = snapshot.child("vendors").child(user.getVendorID()).getValue(Vendor.class);
        List<BillItem> menuItems  = vendor.getMenu();

        // Add the buttons
        for (int i = 0; i < menuItems.size(); i++) {
          // Set button params
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
          Button but = new Button(context);
          but.setId(i);
          but.setText(menuItems.get(i).getName() + ": $" + menuItems.get(i).getPrice());
          but.setOnClickListener(listener);
          menu.addView(but);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("EditMenu.java", error.getMessage());
      }
    });

    // Generate the QR code when the generate button is pressed
    generateBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        String data = qrValue;

        QRGEncoder qrgEncoder = new QRGEncoder(data, null,
                QRGContents.Type.TEXT,500);
        try {
          Bitmap qrBits = qrgEncoder.getBitmap();
          qrImage.setImageBitmap(qrBits);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public String setQRValue (String str) {
    if (qrValue.isEmpty()) return qrValue = str;
    else return qrValue += ", " + str;
  }
}