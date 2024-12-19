package com.snapspace.repository;

import com.snapspace.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
}
