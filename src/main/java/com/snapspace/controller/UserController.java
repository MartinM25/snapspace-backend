package com.snapspace.controller;

import com.snapspace.dto.UserLoginRequest;
import com.snapspace.model.User;
import com.snapspace.service.UserService;
import com.snapspace.dto.UserRegistrationRequest;
import com.snapspace.dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
    try {
      // Use Lombok-generated getters from UserRegistrationRequest
      User user = userService.registerUser(
          request.getFullName(),
          request.getUsername(),
          request.getEmail(),
          request.getPassword()
      );
      return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
    try {
      String token = userService.loginUser(request.getUsernameOrEmail(), request.getPassword());
      return ResponseEntity.ok(token);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/profile")
  public ResponseEntity<UserProfileResponse> getProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      System.err.println("No authenticated user found in security context.");
      return ResponseEntity.status(401).build();
    }

    String username = authentication.getName();
    System.out.println("Authenticated user: " + username);

    try {
      UserProfileResponse profile = userService.getProfileByUsername(username);
      System.out.println("User profile retrieved: " + profile.getUsername());
      return ResponseEntity.ok(profile);
    } catch (Exception e) {
      System.err.println("Error retrieving profile: " + e.getMessage());
      return ResponseEntity.status(404).body(null);
    }
  }
}
