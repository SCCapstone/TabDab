package com.example.tabdab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditVendorInfoFragment extends Fragment {
  private TextView headerText;
  private Button butSave;
  private EditText editName;

  private User user;
  private DatabaseReference vendorDb;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // Get the user from the previous fragment and then initialize the bill
      String userStr = getArguments().getString("userStr", "");
      user = User.fromJson(userStr);

      // Get the vendor information
      vendorDb = FirebaseDatabase.getInstance().getReference("vendors").child(user.getVendorID());
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_vendor_info, container, false);
    headerText = view.findViewById(R.id.editVendorText);
    editName = view.findViewById(R.id.editVendorName);
    butSave = view.findViewById(R.id.saveEditBut);

    // When the save button is clicked save the information
    butSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Check the vendor name in the text box is valid
        if (editName.getText().toString().trim().isEmpty()) {
          Toast.makeText(getContext(), "Vendor must have a name", Toast.LENGTH_SHORT).show();
        } else {  // Name is valid so change it
          vendorDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              vendorDb.child("name").setValue(editName.getText().toString().trim());
              Toast.makeText(getContext(), "Name changed", Toast.LENGTH_SHORT).show();

              // Go back to the vendor menu
              FragmentTransaction ft;
              ft = getParentFragmentManager().beginTransaction();
              ft.replace(R.id.fragment_container, VendorMenuFragment.newInstance(user)).commit();
              ft.addToBackStack(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              Log.d("EditVendorInfoFrag", error.getMessage());
            }
          });
        }
      }
    });
    return view;
  }

  public EditVendorInfoFragment() {
    // Required empty public constructor
  }


  public static EditVendorInfoFragment newInstance(User user) {
    EditVendorInfoFragment fragment = new EditVendorInfoFragment();
    String userStr = user.toJson();
    Bundle args = new Bundle();
    args.putString("userStr", userStr);
    fragment.setArguments(args);
    return fragment;
  }
}