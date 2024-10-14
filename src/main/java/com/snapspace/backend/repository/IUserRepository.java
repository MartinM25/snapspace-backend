package com.snapspace.backend.repository;

import com.snapspace.backend.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
