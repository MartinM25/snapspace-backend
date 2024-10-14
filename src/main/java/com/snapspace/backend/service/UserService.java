package com.snapspace.backend.service;

import com.snapspace.backend.model.User;
import com.snapspace.backend.repository.IUserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
