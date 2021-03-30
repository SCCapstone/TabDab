package com.example.tabdab;

public class Friend {
  String firstName;
  String lastName;
  String email;
  String userId;

  // Constructors
  public Friend () {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
  }
  public Friend (String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // Getters
  public String getFirstName () {
    return this.firstName;
  }
  public String getLastName () {
    return this.lastName;
  }
  public String getEmail () {
    return this.email;
  }

  // Setters
  public void setFirstName (String firstName) {
    this.firstName = firstName;
  }
  public void setLastName (String lastName) {
    this.lastName = lastName;
  }
  public void setEmail (String email) {
    this.email = email;
  }
}
