package org.example.library_management.repository;

import org.example.library_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNationalId(String nationalId);
    Optional<User> findByNationalId(String nationalId);
}
