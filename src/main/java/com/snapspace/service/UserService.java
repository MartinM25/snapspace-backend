package com.snapspace.service;

import com.snapspace.model.User;
import com.snapspace.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User registerUser(String fullName, String username, String email, String password) {

    // Check if username or email already exists
    if(userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }

    if(userRepository.findByUsername(username).isPresent()) {
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
}
