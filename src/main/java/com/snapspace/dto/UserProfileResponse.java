package com.snapspace.dto;

public class UserProfileResponse {
  private String id;
  private String fullName;
  private String username;
  private String email;
  private String profilePic;

  public UserProfileResponse(String id, String fullName, String username, String email, String profilePic) {
    this.id = id;
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.profilePic = profilePic;
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
}
