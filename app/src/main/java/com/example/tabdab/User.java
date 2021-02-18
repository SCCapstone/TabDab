package com.example.tabdab;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
  String firstName, lastName, email, vendorID, cardNum, expDate, CVV;
  boolean isVendor;
  List<Bill> pastPayments;

  public User() {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.isVendor = false;
    this.vendorID = "";
    this.cardNum = "";
    this.expDate = "";
    this.CVV = "";
    this.pastPayments = new ArrayList<>();
    this.pastPayments.add(new Bill());
  }

  public User (String firstName, String lastName, String email, boolean isVendor,
               String vendorID, String cardNum, String expDate, String CVV) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.isVendor = isVendor;
    this.vendorID = vendorID;
    this.cardNum = cardNum;
    this.expDate = expDate;
    this.CVV = CVV;
    this.pastPayments = new ArrayList<>();

    // For firebase purposes
    BillItem item = new BillItem(1, "test");
    Bill bill = new Bill();
    bill.addBillItem(item);
    this.pastPayments.add(bill);
  }

  // Setters
  public void setIsVendor (boolean val) {this.isVendor = val;}
  public void setFirstName (String name) {this.firstName = name;}
  public void setLastName (String name) {this.lastName = name;}
  public void setEmail (String email) {
    if(validEmail(email)) {
      this.email = email;
    }
    else {
      //unsure what to set it as?
    }
  }
  public void setVendorID (String vendorID) {this.vendorID = vendorID;}
  public void setCardNum (String cardNum) {
    if(validCardNum(cardNum)) {
      this.cardNum = cardNum;
    }
    else {
      //unsure what to set it as?
    }
  }
  public void setExpDate (String expDate) {
    if(expDateChecker(expDate)) {
      this.expDate = expDate;
    }
    else {
      //unsure what to set is as?
    }
  }
  public void setCVV (String CVV) {
    if(cvvChecker(CVV)) {
      this.CVV = CVV;
    }
    else {
      //unsure what to set it as?
    }
  }
  public void setPastPayments (List<Bill> pastPayments) {this.pastPayments = pastPayments;}

  // Getters
  public String getFirstName () {return this.firstName;}
  public String getLastName () {return this.lastName;}
  public String getEmail () {return this.email;}
  public String getVendorID () {return this.vendorID;}
  public boolean getIsVendor () {return this.isVendor;}
  public String getCardNum () {return this.cardNum;}
  public String getExpDate () {return this.expDate;}
  public String getCVV () {return this.CVV;}
  public List<Bill> getPastPayments () {return this.pastPayments;}

  public void addPastPayment (Bill bill) {this.pastPayments.add(bill);
  }

  public String toString () {
    return this.firstName + " " + this.lastName + "\n" + this.email + "\n" + this.isVendor + "\n" + this.vendorID + "\n" + this.cardNum + "\n" + this.expDate + "\n" + this.CVV;
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
      if (cardNum.length() >= 12 || cardNum.length() <= 19) {
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
      System.out.println("Length check");
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
          System.out.println("Sorry");
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
        System.out.println("valid");
        return true;
      } catch (NumberFormatException e) {
        System.out.println(CVV + " is not a valid cvv number");
        return false;
      }
    } //cvv's can only be 3 and sometimes 4 in length
    else {
      return false;
    }
  }
}
