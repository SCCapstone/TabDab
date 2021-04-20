package com.example.tabdab;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QrScannerFragment extends Fragment {
  // https://github.com/yuriy-budiyev/code-scanner

  // Define scanner and and scanner result string for use throughout the activity
  CodeScanner codeScanner;
  User user;
  MainActivity ma;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();
  }

  @Nullable
  @Override
  public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final Activity activity = getActivity();
    View view = inflater.inflate(R.layout.fragment_qr_scanner, container, false);

    // Define and create camera and scanner
    CodeScannerView scannerView = view.findViewById(R.id.scannerView);
    codeScanner = new CodeScanner(activity, scannerView);

    // Decode the QR code
    codeScanner.setDecodeCallback(new DecodeCallback() {
      @Override
      public void onDecoded(@NonNull final Result result) {
        // Run in a separate thread for better app performance
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            // Launch the bill view activity when a QR code is decoded
            String message = result.getText();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, BillViewFragment.newInstance(message)).commit();
            ft.addToBackStack(null);
          }
        });
      }
    });

    // Start camera (qr code scanner)
    scannerView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        codeScanner.startPreview();
      }
    });
    return view;
  }

  // Start camera when the user comes back to the app
  @Override
  public void onResume() {
    super.onResume();
    codeScanner.startPreview();
  }

  public static QrScannerFragment newInstance() {
    return new QrScannerFragment();
  }
  public static QrScannerFragment newInstance(User user) {
    QrScannerFragment qrScannerFragment = new QrScannerFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    qrScannerFragment.setArguments(args);
    return qrScannerFragment;
  }
}