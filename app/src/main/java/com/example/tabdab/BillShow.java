package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class BillShow extends AppCompatActivity {
  ImageView qrImage;
  Button doneBtn;
  String qrResult;
  Bill bill;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bill_show);

    Intent intent = getIntent();
    qrImage = findViewById(R.id.qrPlaceHolder);
    doneBtn = findViewById(R.id.done_btn);
    qrResult = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

    // Get and display data from qr scanner activity
    bill = Bill.fromJson(qrResult);


    // Display the qr code
    QRGEncoder qrgEncoder = new QRGEncoder(qrResult, null,
            QRGContents.Type.TEXT,500);
    try {
      Bitmap qrBits = qrgEncoder.getBitmap();
      qrImage.setImageBitmap(qrBits);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Set done button onClickListener
    doneBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), BillCreator.class));
      }
    });
  }
}