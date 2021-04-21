package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillViewFragment extends Fragment {
  private LinearLayout itemizedView;
  private LinearLayout tipLayout;
  private TextView grandTotalView, editTip;
  private Button butAddTip, butPay;
  private Bill bill;
  private String qrResult;

  private DatabaseReference userDb, vendorDb, dailyTotalsDb, paymentsDb, billsDb, payedBillsDb;
  private FirebaseUser userRef;
  private User user;
  private DailyTotals dailyTotals;
  private PaymentTracker paymentTracker;

  MainActivity ma;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();

    // Get the QR code contents from the previous fragment
    qrResult = getArguments().getString("qrResult", "");

    // Get database information
    userRef = FirebaseAuth.getInstance().getCurrentUser();
    userDb = FirebaseDatabase.getInstance().getReference().child("users").child(user.getEmail().replace('.', '*'));
  }

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_bill_view, container, false);
    itemizedView = view.findViewById(R.id.itemized_view);
    grandTotalView = view.findViewById(R.id.grand_total_view);
    editTip = view.findViewById(R.id.editTip);
    butPay = view.findViewById(R.id.pay_button);
    butAddTip = view.findViewById(R.id.addTipButton);
    tipLayout = new LinearLayout(getContext());

    // Check that the qr code contained a reference to a tabdab bill
    if (qrResult.contains("https://tabdab-a9e3a.firebaseio.com/")) {
      // Get and display data from qr scanner fragment
      billsDb = FirebaseDatabase.getInstance().getReferenceFromUrl(qrResult);
      billsDb.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          bill = snapshot.getValue(Bill.class);
          vendorDb = FirebaseDatabase.getInstance().getReference("vendors").child(bill.getVendorId());

          // Check to make sure the bill hasn't already been payed
          payedBillsDb = FirebaseDatabase.getInstance().getReference("payed_bills").child(user.getEmail().replace('.', '*'));
          payedBillsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              // If the bill hasn't already been payed then set up the UI
              if (!snapshot.hasChild(billsDb.getKey())) {
                // Set UI components of the bill for the user
                grandTotalView.setText("$" + String.format("%.2f", bill.getGrandTotal()));
                TextView vendorName = new TextView(getContext());
                TextView date = new TextView(getContext());
                vendorName.setText(bill.getVendor());
                vendorName.setTextColor(Color.WHITE);
                vendorName.setTextSize(35);
                date.setText(bill.getDate());
                date.setTextColor(Color.WHITE);
                itemizedView.addView(vendorName);
                itemizedView.addView(date);

                // Setup each item in the bill
                for (int i = 0; i < bill.getItemizedBill().size(); i++) {
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
                  itemName.setText(bill.getItemizedBill().get(i).getName());
                  itemName.setTextColor(Color.WHITE);
                  itemName.setLayoutParams(weight);
                  itemPrice.setText("$" + String.format("%.2f", bill.getItemizedBill().get(i).getPrice()));
                  itemPrice.setTextColor(Color.WHITE);

                  // Add the price and name
                  item.addView(itemName);
                  item.addView(itemPrice);
                  itemizedView.addView(item);
                }

                // Pay button on click listener
                butPay.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick (View view) {
                    pay();
                  }
                });
              }
              // The bill has already been paid
              else {
                // Don't show the buttons since they wont do anything
                butAddTip.setVisibility(View.INVISIBLE);
                butPay.setVisibility(View.INVISIBLE);
                editTip.setVisibility(View.INVISIBLE);

                // Let the user know that the bill has already been payed
                TextView billAlreadyPayed = new TextView(getContext());
                billAlreadyPayed.setText("Bill has already been payed.");
                billAlreadyPayed.setTextColor(Color.WHITE);
                billAlreadyPayed.setGravity(Gravity.CENTER_HORIZONTAL);
                itemizedView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                itemizedView.setGravity(Gravity.CENTER_HORIZONTAL);
                itemizedView.addView(billAlreadyPayed);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              Log.d("BillViewFragment", error.getMessage());
            }
          });
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
          Log.d("BillViewFragment", error.getMessage());
        }
      });
    } else {  // Bill isn't found in the database
      // Let the user know that the bill wasn't found
      TextView billNotFound = new TextView(getContext());
      billNotFound.setText("Bill not found.");
      billNotFound.setTextColor(Color.WHITE);

      // Don't show the buttons since they wont do anything
      itemizedView.addView(billNotFound);
      butAddTip.setVisibility(View.INVISIBLE);
      butPay.setVisibility(View.INVISIBLE);
      editTip.setVisibility(View.INVISIBLE);
    }

    // Add tip
    butAddTip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View view) {
        addTip();
      }
    });

    return view;
  }

  public static BillViewFragment newInstance() {
    return new BillViewFragment();
  }

  public static BillViewFragment newInstance (String qrResult) {
    BillViewFragment billView = new BillViewFragment();
    Bundle args = new Bundle();
    args.putString("qrResult", qrResult);
    billView.setArguments(args);
    return billView;
  }

  // On click listener functions
  private void pay () {
    // Update the user locally and in the database
    bill.setGrandTotal();
    user.addPastPayment(bill);
    ma.mainActSetUser(user);

    Map<String, Object> updatedUser = new HashMap<>();
    updatedUser.put("pastPayments", user.getPastPayments());
    userDb.updateChildren(updatedUser);
    //userDb.child("pastPayments").setValue(user.getPastPayments());

    // Track the payment in firebase for purposes of CSCE 490
    paymentTracker = new PaymentTracker(user.getFirstName() + " " + user.getLastName(),
            bill.getVendor(), bill.getGrandTotal(), bill.getTip());
    paymentsDb = FirebaseDatabase.getInstance().getReference("payments").push();
    paymentsDb.setValue(paymentTracker);

    // Mark the bill as payed in the database so that it cannot be payed twice
    FirebaseDatabase.getInstance().getReference("payed_bills").child(user.getEmail()
            .replace('.','*')).child(billsDb.getKey()).setValue(true);

    // Let the user know that bill is payed
    Toast.makeText(getContext(), "Bill Payed!", Toast.LENGTH_SHORT).show();

    // Update the daily totals for the vendor
    dailyTotalsDb = FirebaseDatabase.getInstance().getReference("daily_totals").child(bill.getVendorId());
    dailyTotalsDb.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        dailyTotals = snapshot.getValue(DailyTotals.class);
        dailyTotals.addPreviousPayment(bill);
        dailyTotals.addDailyTotalItems(bill);

        dailyTotalsDb.child("previousPayments").setValue(dailyTotals.getPreviousPayments());
        dailyTotalsDb.child("totals").setValue(dailyTotals.getTotals());

        // Go back the QR scanner
        FragmentTransaction ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, QrScannerFragment.newInstance()).commit();
        ft.addToBackStack(null);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d("BillViewFragment", error.getMessage());
      }
    });
  }
  private void addTip () {
    if (editTip.getText().toString().isEmpty()) {
      editTip.setError("Tip cannot be empty.");
    } else {
      // Update the bill and UI
      bill.setTip(Double.parseDouble(editTip.getText().toString()));
      grandTotalView.setText("$" + String.format("%.2f", bill.getGrandTotal() + bill.getTip()));

      // Clear the edit tip edit text view
      editTip.setText("");

      // Update the itemized bill
      itemizedView.removeView(tipLayout);
      tipLayout.removeAllViews();
      TextView itemName = new TextView(getContext());
      TextView itemPrice = new TextView(getContext());

      // Set the linear layout parameters
      LinearLayout.LayoutParams weight = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
              1.0f);
      tipLayout.setLayoutParams(weight);
      tipLayout.setGravity(Gravity.RIGHT);

      // Set the name and price up
      itemName.setText("Tip");
      itemName.setTextColor(Color.WHITE);
      itemName.setLayoutParams(weight);
      itemPrice.setText("$" + String.format("%.2f", bill.getTip()));
      itemPrice.setTextColor(Color.WHITE);

      // Add the price and name
      tipLayout.addView(itemName);
      tipLayout.addView(itemPrice);
      itemizedView.addView(tipLayout);
    }
  }
}
