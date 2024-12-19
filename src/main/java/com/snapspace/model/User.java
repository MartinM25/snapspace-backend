package com.snapspace.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User  {

  @Id
  private String id;
  private String fullName;
  private String username;
  private String email;
  private String profilePic;
  private String password;

  public User(String fullName, String username, String email, String profilePic, String password) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.profilePic = profilePic;
    this.password = password;
  }
}
