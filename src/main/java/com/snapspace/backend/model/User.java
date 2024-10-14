package com.snapspace.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Username is required")
  @Column(unique = true)
  private String username;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Column(unique = true)
  private String email;
}
