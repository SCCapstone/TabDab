package com.example.tabdab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class DeleteAccountFragment extends Fragment {

    private Button ButDelAcc;
    private TextView delAccPassword;
    MainActivity ma;
    User user;
    FirebaseAuth fAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = (MainActivity)getActivity();
        user = ma.mainActGetUser();
        fAuth = FirebaseAuth.getInstance();
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_delete_account, container, false);


            ButDelAcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return view;
        }



    public static DeleteAccountFragment newInstance(){ return new DeleteAccountFragment(); }
    public static DeleteAccountFragment newInstance(User user) {
       DeleteAccountFragment deleteAccountFragment = new DeleteAccountFragment();
        Bundle args = new Bundle();
        args.putString("user", user.toJson());
        deleteAccountFragment.setArguments(args);
        return deleteAccountFragment;
    }

}

