package com.example.tabdab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VendorMenuFragment extends Fragment {
  TextView vendorName;
  Button butCreateBill, butEditMenu;

  public VendorMenuFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_vendor_menu, container, false);
    vendorName = view.findViewById(R.id.vendorName);
    butCreateBill = view.findViewById(R.id.butMakeBill);
    butEditMenu = view.findViewById(R.id.butEditMenu);

    return view;
  }

  public static VendorMenuFragment newInstance () {return new VendorMenuFragment();}
}