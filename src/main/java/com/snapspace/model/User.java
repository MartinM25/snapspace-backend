package com.snapspace.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  @Id
  private String id;
  private String fullName;
  private String username;
  private String email;
  private String profilePic;
  private String password;

  // Default constructor
  public User() {
  }

  // All-args constructor
  public User(String id, String fullName, String username, String email, String profilePic, String password) {
    this.id = id;
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.profilePic = profilePic;
    this.password = password;
  }

  // Constructor for new user creation
  public User(String fullName, String username, String email, String profilePic, String password) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.profilePic = profilePic;
    this.password = password;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
