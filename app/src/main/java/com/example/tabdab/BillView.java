package com.example.tabdab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class BillView extends AppCompatActivity {

    public static final int GRAND_TOTAL_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

        // Get and display data from qr scanner activity
        Intent intent = getIntent();
        String qrResult = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView itemizedView = findViewById(R.id.itemized_view);
        TextView grandTotalView = findViewById(R.id.grand_total_view);
        itemizedView.setText(decodeString(qrResult));
        grandTotalView.setText(getGrandTotal(qrResult));
    }

    public void pay (View view) {
        Toast payMessage = Toast.makeText(this, R.string.pay_message, Toast.LENGTH_SHORT);
        payMessage.show();
    }

    /**
     *
     * @param str String read from the QR code
     * @return the string formatted for the bill view screen. (Grand total and itemized bill)
     */
    public String decodeString (String str) {
        str = str.substring(str.indexOf(','));  // Get rid of grand total
        return str.replaceAll(",", "\n");
    }

    /**
     *
     * @param str String read from the QR code
     * @return the grand total obtained from the QR code
     */
    public String getGrandTotal (String str) {
        return str.split(",")[GRAND_TOTAL_INDEX];
    }

    // TODO Check that the string read from the QR code is of the TabDab format.
}
