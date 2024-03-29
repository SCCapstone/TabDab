package com.example.tabdab;

import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditInfoFragment extends Fragment {

  private Button saveInfoBut;
  private EditText newFirstName, newLastName, newUserEmail, vendorId, newCardNum, newExpDate, newCVV;
  private Switch switchIsVendor;
  private FirebaseAuth fireAuth;
  private DatabaseReference database;
  private FirebaseUser userRef;
  User user;

  MainActivity ma;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // In case we need to change the header information
    ma = (MainActivity)getActivity();
    user = ma.mainActGetUser();

    fireAuth = FirebaseAuth.getInstance();
    database =  FirebaseDatabase.getInstance().getReference("users/");
    userRef = fireAuth.getCurrentUser();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_info, container, false);

    saveInfoBut = view.findViewById(R.id.saveEditBut);
    newFirstName = view.findViewById(R.id.editFirstName);
    newLastName = view.findViewById(R.id.editLastName);
    newUserEmail = view.findViewById(R.id.editEmailtxt);
    vendorId = view.findViewById(R.id.vendorId);
    switchIsVendor = view.findViewById(R.id.isVendor);
    newCardNum = view.findViewById(R.id.editCardNum);
    newExpDate = view.findViewById(R.id.editExpDate);
    newCVV = view.findViewById(R.id.editCVV);

    // Vendor switch is switched to true, make the vendor ID input visible to the user
    switchIsVendor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (switchIsVendor.isChecked()) {
          vendorId.setVisibility(View.VISIBLE);
        } else {
          vendorId.setVisibility(View.INVISIBLE);
        }
      }
    });

    saveInfoBut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String firstName = newFirstName.getText().toString();
        String lastName = newLastName.getText().toString();
        final String newEmail = newUserEmail.getText().toString().trim();
        String newCard = newCardNum.getText().toString();
        String newDate = newExpDate.getText().toString();
        String newCV = newCVV.getText().toString();
        final String vendorID = vendorId.getText().toString();
        userRef = fireAuth.getCurrentUser();
        // Check if the vendor ID the user entered exists.
        DatabaseReference refVendors = FirebaseDatabase.getInstance().getReference().child("vendors");
        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference().child("users").child(user.getEmail().replace('.', '*'));

        // Check if the vendor ID exists already
        refVendors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (vendorID.isEmpty()) {
                // Do nothing
              } else if (!snapshot.hasChild(vendorID)) {  // Vendor ID not found
                Toast.makeText(getContext(), "Vendor ID not found.", Toast.LENGTH_SHORT).show();
              } else {  // Vendor ID exists. Update the user info
                user.setIsVendor(true);
                user.setVendorID(vendorID);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              Log.d("EditInfo.java", error.getMessage());
            }
          });

        // Check which fields the user has updated and update them
        if (!firstName.isEmpty()) {
          user.setFirstName(firstName);
        }
        if (!lastName.isEmpty()) {
          user.setLastName(lastName);
        }
        if (!newCard.isEmpty() && validCardNum(newCard)) {
          user.setCardNum(newCard);
        }
        if (!newDate.isEmpty() && expDateChecker(newDate)) {
          user.setExpDate(newDate);
        }
        if (!newCV.isEmpty() && cvvChecker(newCV)) {
          user.setCVV(newCV);
        }
        if (!newEmail.isEmpty() && validEmail(newEmail)) {
          final String oldEmail = user.getEmail();

          //Check for if the email is already registered.
          fireAuth.fetchSignInMethodsForEmail(newUserEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
              if (!(task.getResult().getSignInMethods().isEmpty())) {
                Toast.makeText(getContext(), "Email already registered. Please use a different email.", Toast.LENGTH_LONG).show();
              } else {
                user.setEmail(newEmail);
                userRef.updateEmail(newEmail);


                database.child(user.getEmail().replace('.','*')).setValue(user);
                database.child(oldEmail.replace('.','*')).removeValue();

                ma.mainActSetUser(user);
              }
            }
          });
        } else {  // Update the user
          refUser.setValue(user);
          ma.mainActSetUser(user);
        }



        FragmentTransaction ft;
        ft = getParentFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        ft.replace(R.id.fragment_container, SettingsFragment.newInstance()).commit();
        ft.addToBackStack(null);
      }
    });

    return view;
  }

  /**
   * method that tests to see whether an email is a valid entry
   * does not actually test if email actually exists
   * @param email
   * @return false and or a valid email if true
   */
  private static boolean validEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }

  /**
   *
   * @param cardNum
   * @return true or false depending if it ist a "valid" card num
   * checks to see if the string is a whole integer
   * then checks to see if the length requirement is right
   * if met return true, else return false
   */
  private static boolean validCardNum(String cardNum) {
    try {
      // checking valid integer using parseInt() method
      Long.parseLong(cardNum);
      //typical american credit/ debit cards have a length between 12 and 15
      if (cardNum.length() >= 12 && cardNum.length() <= 19) {
        return true;
      } //return false if length requirement isnt met
      else {
        return false;
      }
    }//return false if it isnt an integer to begin with
    catch (NumberFormatException e) {
      return false;
    }
  }
  /**
   * checks to see if the expDate is greater than or equal to the current mm/yyyy
   * Also checks syntax and length
   * @param expDate
   * @return true or false
   */
  private static boolean expDateChecker(String expDate) {
    //check to see length of input mm/yyyy
    if (expDate.length() == 7) {
      //check to see if its proper syntax
      // System.out.println("Length check");
      SimpleDateFormat standard = new SimpleDateFormat("MM/yyyy");
      standard.setLenient(false);
      try {
        Date userInput = standard.parse(expDate);
        Date current = new Date();
        boolean before = userInput.before(new Date());
        //if the current date is before the user input string and or equal return true. Its valid
        if (!before) {
          return true;
        } else if ((userInput.getMonth() == current.getMonth()) && (userInput.getYear() == current.getYear())) {
          return true;
        } else {
          // System.out.println("Sorry");
          return false;
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      return false;
    }
    return false;
  }

  /**
   * checks the length of it, cvv's can only be 3 or 4 in length
   * @param CVV
   * @return true or false based on if its a "valid" cvv number
   */
  private static boolean cvvChecker(String CVV) {
    if (CVV.length() == 3 || CVV.length() == 4) {
      try {
        Integer.parseInt(CVV);
        //System.out.println("valid");
        return true;
      } catch (NumberFormatException e) {
        // System.out.println(CVV + " is not a valid cvv number");
        return false;
      }
    } //cvv's can only be 3 and sometimes 4 in length
    else {
      return false;
    }
  }

  public EditInfoFragment() {
    // Required empty public constructor
  }

  public static EditInfoFragment newInstance() {
    return new EditInfoFragment();
  }
  public static EditInfoFragment newInstance(User user) {
    EditInfoFragment editInfoFragment = new EditInfoFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    editInfoFragment.setArguments(args);
    return editInfoFragment;
  }
}