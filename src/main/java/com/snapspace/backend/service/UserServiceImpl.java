package com.snapspace.backend.service;

import com.snapspace.backend.model.User;
import com.snapspace.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(userRepository.findByUsername(username));
  }
}
