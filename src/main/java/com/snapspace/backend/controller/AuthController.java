package com.snapspace.backend.controller;

import com.snapspace.backend.model.User;
import com.snapspace.backend.security.JwtUtil;
import com.snapspace.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private IUserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Register a new user
  @PostMapping("/register")
  public String registerUser(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
    userService.saveUser(user);
    return "User registered successfully";
  }

  // Login and return JWT token
  @PostMapping("/login")
  public String loginUser(@RequestBody User user) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
      );

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      return jwtUtil.generateToken(userDetails.getUsername());

    } catch (AuthenticationException e) {
      return "Invalid username or password";
    }
  }

}
