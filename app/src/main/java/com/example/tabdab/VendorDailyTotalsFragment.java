package com.example.tabdab;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VendorDailyTotalsFragment extends Fragment {
  ScrollView scroller;
  LinearLayout totalsLinearLayout;
  CalendarView calendar;

  User user;
  DailyTotals dailyTotals;
  DatabaseReference dailyTotalsDb;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // Get the user from the previous fragment and then initialize the bill
      String userStr = getArguments().getString("userStr", "");
      user = User.fromJson(userStr);
    }
    dailyTotalsDb = FirebaseDatabase.getInstance().getReference("daily_totals").child(user.getVendorID());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_vendor_daily_totals, container, false);

    scroller = view.findViewById(R.id.scroller);
    totalsLinearLayout = view.findViewById(R.id.payments);
    calendar = view.findViewById(R.id.calendarView);

    // Get the vendor the user belongs to then get the daily totals for the date selected
    // Set the date change listener
    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
      @Override
      public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        // Get the selected date and make the date firebase friendly (string with no '/')
        String monthStr = "";
        if (month < 10) monthStr = "0" + (month+1);
        final String selectedDate = monthStr + " " + dayOfMonth + " " + year;

        dailyTotalsDb.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            dailyTotals = snapshot.getValue(DailyTotals.class);

            // Set the daily totals text
            totalsLinearLayout.removeAllViews();
            TextView totalsText = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,10);
            totalsText.setId(0);
            totalsText.setText(dailyTotals.totalsListToTotalsStr(selectedDate));
            totalsText.setTextColor(Color.WHITE);
            totalsText.setBackground(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.textview_pink, null));
            totalsText.setLayoutParams(params);
            totalsText.setPadding(10,10,10,10);
            totalsLinearLayout.addView(totalsText);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d("VendorDailyTotalsFrag", error.getMessage());
          }
        });
      }
    });


    return view;
  }

  public VendorDailyTotalsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param user User sent in from the vendor menu fragment
   * @return A new instance of fragment vendor_daily_totals.
   */
  public static VendorDailyTotalsFragment newInstance(User user) {
    VendorDailyTotalsFragment fragment = new VendorDailyTotalsFragment();
    String userStr = user.toJson();
    Bundle args = new Bundle();
    args.putString("userStr", userStr);
    fragment.setArguments(args);
    return fragment;
  }

}