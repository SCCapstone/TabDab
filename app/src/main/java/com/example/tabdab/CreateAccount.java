package com.example.tabdab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

  private FirebaseAuth mAuth;
  private EditText uFirstName,uLastName,uPassword,uEmail, vendorId, uCardNum, uExpDate, uCVV;
  private Button uRegister;
  private Switch switchIsVendor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account);

    mAuth = FirebaseAuth.getInstance();
    uEmail = findViewById(R.id.uEmail);
    uFirstName = findViewById(R.id.uFirstName);
    uLastName = findViewById(R.id.uLastName);
    uPassword = findViewById(R.id.uPassword);
    uRegister = findViewById(R.id.btnRegister);
    vendorId = findViewById(R.id.vendorId);
    uCardNum = findViewById(R.id.uCardNum);
    switchIsVendor = findViewById(R.id.isVendor);
    uExpDate = findViewById(R.id.uExpDate);
    uCVV = findViewById(R.id.uCVV);


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

    // Register button is clicked
    uRegister.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        final String email = uEmail.getText().toString().trim();
        final String password = uPassword.getText().toString().trim();
        final String firstName = uFirstName.getText().toString().trim();
        final String lastName = uLastName.getText().toString().trim();
        final String vendorID = vendorId.getText().toString().trim();
        final String cardNum = uCardNum.getText().toString().trim();
        final String expDate = uExpDate.getText().toString().trim();
        final String CVV = uCVV.getText().toString().trim();





        // Errors
        duplicateEmail();
        if (validEmail(email) == false) {
          uEmail.setError("Valid Email Address Required");
          return;
        }

        if (TextUtils.isEmpty(password)) {
          uPassword.setError("Password is required");
          return;
        }
        if (password.length() < 8) {
          uPassword.setError("Password must be at least 8 characters");
          return;
        }
        if ( validCardNum(cardNum) == false) {
          uCardNum.setError("Please enter valid card number");
          return;
        }
        if (expDateChecker(expDate) == false) {
          uExpDate.setError("Please enter valid expiration date");
          return;
        }
        if (cvvChecker(CVV) == false) {
          uCVV.setError("Please enter valid CVV");
          return;
        }
        if (switchIsVendor.isChecked() && TextUtils.isEmpty(vendorID)) {
          vendorId.setError(("Please enter vendor ID"));
          return;
        }
        // Check that the vendor ID exists
        if (!vendorID.isEmpty()) {
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("vendors");
          ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (!snapshot.hasChild(vendorID)) {
                vendorId.setError("Vendor ID not found");
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              Log.d("CreateAccount.java", error.getMessage());
            }
          });
        }

        // Create the user
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              User user = new User(firstName, lastName, email,
                      switchIsVendor.isChecked(), vendorID, cardNum, expDate, CVV);

              //User is given the LOGIN tag so that the user is able to close and reopen the app and still be logged in
              SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.putString("LOGIN", email);
              editor.commit();

              FirebaseDatabase.getInstance().getReference("users/")
                      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                      .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
                    Toast.makeText(CreateAccount.this, "User Created.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                  } else {
                    Toast.makeText(CreateAccount.this,
                            "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                  }
                }
              });
            }
          }
        });
      }
    });
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
   * Method that checks to see if the email entered by the user is already registered
   * in the database or not and provides a prompt that the email is in use if it is.
   */
  private void duplicateEmail() {
    mAuth.fetchSignInMethodsForEmail(uEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
      @Override
      public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
        if(!(task.getResult().getSignInMethods().isEmpty())) {
          Toast.makeText(CreateAccount.this, "Email is already in use, please use a different email.", Toast.LENGTH_SHORT).show();
        }
      }
    });
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
}