package com.example.tabdab;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class DeleteAccountFragment extends Fragment {

    private Button ButDelAcc;
    private TextView delAccPassword;
    private   MainActivity ma;
    private User user;
    private FirebaseAuth fAuth;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = (MainActivity)getActivity();
        user = ma.mainActGetUser();
        fAuth = FirebaseAuth.getInstance();

        // For logout purposes
        sharedPreferences = ma.getSharedPreferences("Preferences",0);
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_delete_account, container, false);

            ButDelAcc = view.findViewById(R.id.deleteAccountBut);
            delAccPassword = view.findViewById(R.id.delAccPass);

           ButDelAcc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String checkPass = delAccPassword.getText().toString().trim();

                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), checkPass);
                    fAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Account successfully deleted!", Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("LOGIN", null);
                                editor.apply();
                                fAuth.getCurrentUser().delete();
                                startActivity(new Intent(ma.getApplicationContext(), Login.class));
                            }
                            else {
                                delAccPassword.setError("Incorrect password");
                            }
                        }
                    });
                }
            });

            return view;
        }

    public DeleteAccountFragment() {
        // Required empty public constructor
    }


    public static DeleteAccountFragment newInstance(User user) {
       DeleteAccountFragment deleteAccountFragment = new DeleteAccountFragment();
        Bundle args = new Bundle();
        args.putString("user", user.toJson());
        deleteAccountFragment.setArguments(args);
        return deleteAccountFragment;
    }
    public static DeleteAccountFragment newInstance(){ return new DeleteAccountFragment(); }
}

