package com.example.tabdab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VendorDailyTotalsFragment extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "user";
  User user;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // Get the user from the previous fragment and then initialize the bill
      String userStr = getArguments().getString("userStr", "");
      user = User.fromJson(userStr);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_vendor_daily_totals, container, false);
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