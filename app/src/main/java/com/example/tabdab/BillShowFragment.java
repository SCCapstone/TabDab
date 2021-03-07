package com.example.tabdab;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class BillShowFragment extends Fragment {
  // fragment initialization parameters
  private static final String QR_RESULT = "qrResult";
  ImageView qrImage;
  Button butDone;
  Bill bill;
  private String qrResult;

  public BillShowFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      qrResult = getArguments().getString(QR_RESULT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_bill_show, container, false);
    qrImage = view.findViewById(R.id.qrPlaceHolder);
    butDone = view.findViewById(R.id.done_btn);

    // Get and display data from qr scanner activity
    bill = Bill.fromJson(qrResult);

    // Display the qr code
    QRGEncoder qrgEncoder = new QRGEncoder(qrResult, null,
            QRGContents.Type.TEXT,500);
    try {
      Bitmap qrBits = qrgEncoder.getBitmap();
      qrImage.setImageBitmap(qrBits);
    } catch (Exception e) {
      e.printStackTrace();
    }

    butDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick (View v) {
        FragmentTransaction ft;
        ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, QrScannerFragment.newInstance()).commit();
        ft.addToBackStack(null);
      }
    });


    return view;
  }

  public static BillShowFragment newInstance () { return new BillShowFragment(); }

  /**
   *
   * @param qrResult QR code recieved from the create bill fragment
   * @return A new instance of fragment BillShowFragment.
   */
  public static BillShowFragment newInstance(String qrResult) {
    BillShowFragment fragment = new BillShowFragment();
    Bundle args = new Bundle();
    args.putString(QR_RESULT, qrResult);
    fragment.setArguments(args);
    return fragment;
  }
}