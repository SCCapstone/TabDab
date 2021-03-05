package com.example.tabdab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RegisterVendorFragment extends Fragment {
  TextView vendorName;
  Button register;
  DatabaseReference vendors;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_register_vendor, container, false);
    vendorName = view.findViewById(R.id.editVendorName);
    register = view.findViewById(R.id.butRegisterVendor);

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        makeVendor(vendorName.getText().toString().trim());
      }
    });
    return view;
  }

  /**
   * Generates a unique vendorID and stores the vendor name inside of the database
   *
   * @param vendorName Name of the vendor
   */
  public void makeVendor(String vendorName) {
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    vendors = FirebaseDatabase.getInstance().getReference("vendors").push();
    List<BillItem> menu = new ArrayList<>();
    Vendor vendor = new Vendor(vendors.getKey(), vendorName, menu);
    vendors.setValue(vendor);

    // Update data in firebase.
    FirebaseDatabase.getInstance().getReference("users/").child(uid).child("vendorID").setValue(vendors.getKey());
    FirebaseDatabase.getInstance().getReference("users/").child(uid).child("isVendor").setValue(true);

    Toast.makeText(getContext(), "Vendor Registered", Toast.LENGTH_SHORT).show();

    // Launch the vendor fragment
    FragmentTransaction ft;
    ft = getParentFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_container, VendorMenuFragment.newInstance()).commit();
    ft.addToBackStack(null);
  }

  public RegisterVendorFragment() {
    // Required empty public constructor
  }


  public static RegisterVendorFragment newInstance() {
    RegisterVendorFragment fragment = new RegisterVendorFragment();
    return fragment;
  }

}