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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private Button ButRegisterVendor, ButChangePass, ButPreviousPayments, ButEditInfo, ButEditMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButRegisterVendor = v.findViewById(R.id.registerVendorButton);
        ButChangePass = v.findViewById(R.id.chngPassBut);
        ButPreviousPayments = v.findViewById(R.id.ButPreviousPayments);
        ButEditInfo = v.findViewById(R.id.editInfoBut);
        ButEditMenu = v.findViewById(R.id.ButEditMenu);

        ButRegisterVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CreateVendor.class));
            }
        });

        ButChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePass = new Intent(view.getContext(), ChangePassword.class);
                startActivity(changePass);
            }
        });
        ButEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editAcctInfo = new Intent(view.getContext(), EditInfo.class);
                startActivity(editAcctInfo);
            }
        });
        ButEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), EditMenu.class));
            }
        });
            return v;
}

        public static SettingsFragment newInstance () {
            return new SettingsFragment();
        }
    }
