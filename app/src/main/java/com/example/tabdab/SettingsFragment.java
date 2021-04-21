package com.example.tabdab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {
    private Button ButRegisterVendor, ButChangePass, ButEditInfo, ButDeleteAccount;
    User user;
    MainActivity ma;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ma = (MainActivity)getActivity();
        user = ma.mainActGetUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButRegisterVendor = v.findViewById(R.id.registerVendorButton);
        ButChangePass = v.findViewById(R.id.chngPassBut);
        ButEditInfo = v.findViewById(R.id.editInfoBut);
        ButDeleteAccount = v.findViewById(R.id.deleteAccountBut);

        ButRegisterVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft;
                ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                ft.replace(R.id.fragment_container, RegisterVendorFragment.newInstance()).commit();
                ft.addToBackStack(null);
            }
        });
        ButChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft;
                ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                ft.replace(R.id.fragment_container, ChangePasswordFragment.newInstance()).commit();
                ft.addToBackStack(null);
            }
        });
        ButEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft;
                ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                ft.replace(R.id.fragment_container, EditInfoFragment.newInstance()).commit();
                ft.addToBackStack(null);
            }
        });
        ButDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft;
                ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                ft.replace(R.id.fragment_container, DeleteAccountFragment.newInstance()).commit();
                ft.addToBackStack(null);
            }
        });

        return v;
    }

    public static SettingsFragment newInstance () {
        return new SettingsFragment();
    }
    public static SettingsFragment newInstance (User user) {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("user", user.toJson());
        settingsFragment.setArguments(args);
        return settingsFragment;
    }
}
