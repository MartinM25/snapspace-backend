package com.snapspace.backend.service;

import java.util.Optional;
import com.snapspace.backend.model.User;

public interface IUserService {
  User saveUser(User user);
  Optional<User> findByUsername(String username);
}
