package com.snapspace.controller;

import com.snapspace.dto.UserLoginRequest;
import com.snapspace.model.User;
import com.snapspace.service.UserService;
import com.snapspace.dto.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
