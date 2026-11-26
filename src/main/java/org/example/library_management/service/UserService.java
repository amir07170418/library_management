package org.example.library_management.service;

import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.exception.LibraryException;
import org.example.library_management.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private UserResponse save(UserRequest userRequest) {
        if (!userRepository.existsByNationalId(userRequest.getNationalId())) {
            throw new LibraryException(HttpStatus.NOT_FOUND, "User with NationalId " + userRequest.getNationalId() + " not found");
        }
    }
}
