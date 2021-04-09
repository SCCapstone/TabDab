package com.example.tabdab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordFragment extends Fragment {
  private EditText userEmail;
  private Button resetPassBut;
  private FirebaseAuth fireAuth;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_change_password, container, false);
    userEmail = view.findViewById(R.id.chngPassEmail);
    resetPassBut = view.findViewById(R.id.sndEmailBut);

    fireAuth = FirebaseAuth.getInstance();

    resetPassBut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String email = userEmail.getText().toString().trim();

        if (email.isEmpty()) {
          userEmail.setError("Please enter your email address.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
          userEmail.setError("This email does not belong to any account, please enter a different email address.");
        } else {
          fireAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                Toast.makeText(getContext(), "An email has been sent to reset your password.", Toast.LENGTH_SHORT).show();
              } else
                Toast.makeText(getContext(), "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
            }
          });
        }
      }
    });

    return view;
  }

  public ChangePasswordFragment() {
    // Required empty public constructor
  }

  public static ChangePasswordFragment newInstance() {
    ChangePasswordFragment fragment = new ChangePasswordFragment();
    return fragment;
  }
}