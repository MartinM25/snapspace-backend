package com.snapspace.dto;

public class UserRegistrationRequest {
  private String fullName;
  private String username;
  private String email;
  private String password;

  // Getter and Setter for fullName
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  // Getter and Setter for username
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // Getter and Setter for email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // Getter and Setter for password
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
