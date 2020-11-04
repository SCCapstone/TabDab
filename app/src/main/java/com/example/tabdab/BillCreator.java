package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class BillCreator extends AppCompatActivity {

    // TODO Create a "bill" class to handle qr code data, getGrandTotal, toQRCode, etc.

    // Set up ui elements
    Button generateBtn;
    Button menuItem1Btn;
    Button menuItem2Btn;
    Button menuItem3Btn;
    ImageView qrImage;
    TextView itemizedBill;
    String qrValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_creator);

        // Set up the UI layout
        generateBtn = findViewById(R.id.generate_bill_btn);
        menuItem1Btn = findViewById(R.id.menu_item1);
        menuItem2Btn = findViewById(R.id.menu_item2);
        menuItem3Btn = findViewById(R.id.menu_item3);
        qrImage = findViewById(R.id.qrPlaceHolder);
        itemizedBill = findViewById(R.id.itemized_bill);
        qrValue = "$11";

        // Generate the QR code string when menu items are selected
        menuItem1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                qrValue = setQRValue(menuItem1Btn);
                itemizedBill.setText(qrValue);
            }
        });
        menuItem2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                qrValue = setQRValue(menuItem2Btn);
                itemizedBill.setText(qrValue);
            }
        });
        menuItem3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                qrValue = setQRValue(menuItem3Btn);
                itemizedBill.setText(qrValue);
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

    public String setQRValue (Button btn) {
        if (qrValue.isEmpty()) return qrValue = btn.getText().toString();
        else return qrValue += ", " + btn.getText().toString();
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_creator);
    }*/
}