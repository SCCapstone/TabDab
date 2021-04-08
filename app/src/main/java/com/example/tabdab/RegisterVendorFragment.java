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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterVendorFragment extends Fragment {
  private TextView vendorName;
  private Button register;
  private DatabaseReference vendors;
  User user;
  MainActivity ma;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();
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
    // Add the vendor to firebase
    vendors = FirebaseDatabase.getInstance().getReference("vendors").push();
    List<BillItem> menu = new ArrayList<>();
    Vendor vendor = new Vendor(vendors.getKey(), vendorName, menu);
    vendors.setValue(vendor);

    // Add the daily totals info to firebase
    ArrayList<Bill> previousPayments = new ArrayList<>();
    HashMap<String, List<BillItem>> totals = new HashMap<>();
    DailyTotals dailyTotals = new DailyTotals(vendor.getVendorId(), previousPayments, totals);
    FirebaseDatabase.getInstance().getReference("daily_totals").child(dailyTotals.getVendorId()).setValue(dailyTotals);

    // Update user data in firebase.
    user.setVendorID(vendors.getKey());
    FirebaseDatabase.getInstance().getReference("users/").child(user.getEmail().replace('.', '*')).child("vendorID").setValue(vendors.getKey());
    user.setIsVendor(true);
    FirebaseDatabase.getInstance().getReference("users/").child(user.getEmail().replace('.', '*')).child("isVendor").setValue(true);
    Toast.makeText(getContext(), "Vendor Registered", Toast.LENGTH_SHORT).show();

    ma.mainActSetUser(user);

    // Launch the vendor menu fragment
    FragmentTransaction ft;
    ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
            R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    ft.replace(R.id.fragment_container, VendorMenuFragment.newInstance()).commit();
    ft.addToBackStack(null);
  }

  public RegisterVendorFragment() {
    // Required empty public constructor
  }


  public static RegisterVendorFragment newInstance() {
    return new RegisterVendorFragment();
  }
  public static RegisterVendorFragment newInstance(User user) {
    RegisterVendorFragment registerVendorFragment = new RegisterVendorFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    registerVendorFragment.setArguments(args);
    return registerVendorFragment;
  }

}