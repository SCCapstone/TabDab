package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class PastPaymentsFragment extends Fragment {
  private LinearLayout payments;
  private User user;
  MainActivity ma;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_past_payments, container, false);

    ma = (MainActivity) getActivity();
    user = ma.mainActGetUser();

    // UI elements
    payments = view.findViewById(R.id.payments);

    // If the user doesn't have any past payments let them know, otherwise show them
    if (user.getPastPayments().isEmpty() || user.getPastPayments() == null ||
            user.getPastPayments().size() == 1) {  // There is always at least a dummy past payment
      TextView noPastPayments = new TextView(getContext());
      noPastPayments.setText("No previous payments.");
      noPastPayments.setTextColor(Color.WHITE);
      payments.addView(noPastPayments);
    } else {
      List<Bill> prevPayments = user.getPastPayments();

      // Set the text views parameters and add it
      for (int i = prevPayments.size() - 1; i > 0; i--) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 10);
        LinearLayout bill = new LinearLayout(getContext());

        TextView date = new TextView(getContext());
        TextView vendorName = new TextView(getContext());

        // Set the past payment parameters
        bill.setId(i);
        bill.setOrientation(LinearLayout.VERTICAL);
        bill.setBackground(ResourcesCompat.getDrawable(getResources(),
                R.drawable.textview_pink, null));
        bill.setPadding(10, 10, 10, 10);
        bill.setLayoutParams(params);

        // Set the text parameters
        date.setLayoutParams(params);
        date.setText(prevPayments.get(i).getDate());
        date.setTextColor(Color.WHITE);
        vendorName.setText(prevPayments.get(i).getVendor());
        vendorName.setTextSize(35);
        vendorName.setTextColor(Color.WHITE);
        vendorName.setLayoutParams(params);

        // Add the text views to the previous payment
        bill.addView(vendorName);
        bill.addView(date);

        // Set the itemized bill portion of the payment
        for (int j = 0; j < prevPayments.get(i).getItemizedBill().size(); j++) {
          TextView itemName = new TextView(getContext());
          TextView itemPrice = new TextView(getContext());
          LinearLayout item = new LinearLayout(getContext());

          // Set the linear layout parameters
          LinearLayout.LayoutParams weight = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                  1.0f);
          item.setLayoutParams(weight);
          item.setGravity(Gravity.RIGHT);

          // Set the name and price up
          itemName.setText(prevPayments.get(i).getItemizedBill().get(j).getName());
          itemName.setTextColor(Color.WHITE);
          itemName.setLayoutParams(weight);
          itemPrice.setText("$" + String.format("%.2f", prevPayments.get(i).getItemizedBill().get(j).getPrice()));
          itemPrice.setTextColor(Color.WHITE);

          // Add the price and name
          item.addView(itemName);
          item.addView(itemPrice);
          bill.addView(item);
        }

        // Set up the tip view
        LinearLayout tipLayout = new LinearLayout(getContext());
        TextView tipText = new TextView(getContext());
        TextView tipPrice = new TextView(getContext());

        LinearLayout.LayoutParams grandTotalWeight = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);
        tipLayout.setLayoutParams(grandTotalWeight);
        tipLayout.setGravity(Gravity.RIGHT);

        // Set the name and price up
        tipText.setText("Tip");
        tipText.setTextColor(Color.WHITE);
        tipText.setLayoutParams(grandTotalWeight);
        tipPrice.setText("$" + String.format("%.2f", prevPayments.get(i).getTip()));
        tipPrice.setTextColor(Color.WHITE);

        // Add the price and name
        tipLayout.addView(tipText);
        tipLayout.addView(tipPrice);
        bill.addView(tipLayout);

        // Set up the grand total view
        LinearLayout grandTotalLayout = new LinearLayout(getContext());
        TextView grandTotalText = new TextView(getContext());
        TextView grandTotalPrice = new TextView(getContext());

        grandTotalLayout.setLayoutParams(grandTotalWeight);
        grandTotalLayout.setGravity(Gravity.RIGHT);

        // Set the name and price up
        grandTotalText.setText("Grand Total ");
        grandTotalText.setTextColor(Color.WHITE);
        grandTotalText.setLayoutParams(grandTotalWeight);
        grandTotalPrice.setText("$" + String.format("%.2f", prevPayments.get(i).getGrandTotal()));
        grandTotalPrice.setTextColor(Color.WHITE);

        // Add the price and name
        grandTotalLayout.addView(grandTotalText);
        grandTotalLayout.addView(grandTotalPrice);
        bill.addView(grandTotalLayout);

        // Add the previous payment
        payments.addView(bill);
      }
    }

    return view;
  }

  public static PastPaymentsFragment newInstance() {
    return new PastPaymentsFragment();
  }
}