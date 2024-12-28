package com.snapspace.service;

import com.snapspace.model.User;
import com.snapspace.util.JwtUtil;
import com.snapspace.dto.UserProfileResponse;
import com.snapspace.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Loading user by username: " + username);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> {
          System.err.println("User not found for username: " + username);
          return new UsernameNotFoundException("User not found");
        });

    System.out.println("User found: " + user.getUsername());
    return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
        .password(user.getPassword())
        .roles("USER") // Adjust roles as needed
        .build();
  }

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

  public User updateUserProfile(String currentUsername, User updatedUser) {
    User existingUser = userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Update fields if provided in the request
    if (updatedUser.getFullName() != null) {
      existingUser.setFullName(updatedUser.getFullName());
    }

    if (updatedUser.getEmail() != null) {
      existingUser.setEmail(updatedUser.getEmail());
    }

    if (updatedUser.getPassword() != null) {
      existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }

    // Save the updated user back to the database
    return userRepository.save(existingUser);
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

  public UserProfileResponse getProfileByUsername(String username) {
    // Find the user by username
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    // Build the response DTO
    return new UserProfileResponse(
        user.getId(),
        user.getFullName(),
        user.getUsername(),
        user.getEmail(),
        user.getProfilePic()
    );
  }


}
