package com.snapspace.service;

import com.snapspace.model.User;
import com.snapspace.repository.IUserRepository;
import com.snapspace.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User registerUser(String fullName, String username, String email, String password) {

    // Check if username or email already exists
    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }

    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }

    // Encrypt password
    String encryptedPassword = passwordEncoder.encode(password);

    // Default profile picture url
    String defaultProfilePic = "https://example.com/default-profile-picture.png";

    // Create and save new user
    User user = new User(fullName, username, email, defaultProfilePic, encryptedPassword);
    return userRepository.save(user);
  }

  public String loginUser(String usernameOrEmail, String password) {
    // Find user by username
    Optional<User> optionalUser = userRepository.findByUsername(usernameOrEmail);

    // If not found by username, try finding by email
    if (optionalUser.isEmpty()) {
      optionalUser = userRepository.findByEmail(usernameOrEmail);
    }

    if (optionalUser.isEmpty()) {
      throw new IllegalArgumentException("Invalid username/email or password");
    }

    User user = optionalUser.get();

    // Validate password
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("Invalid email or password");
    }

    // Generate JWT token
    return JwtUtil.generateToken(user.getId(), user.getUsername());
  }
}
