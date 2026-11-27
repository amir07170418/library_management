package org.example.library_management.service;

import jakarta.transaction.Transactional;
import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.dto.UserUpdate;
import org.example.library_management.exception.LibraryException;
import org.example.library_management.mapper.UserMapper;
import org.example.library_management.model.User;
import org.example.library_management.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public UserResponse save(UserRequest userRequest) {
        if (userRepository.existsByNationalId(userRequest.getNationalId())) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "User with NationalId " + userRequest.getNationalId() + " exist");
        }
        User user = userMapper.toUser(userRequest);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    @Transactional
    public UserResponse update(String nationalId, UserUpdate userUpdate) {
        User user = userRepository.findByNationalId(nationalId).orElseThrow(() -> new LibraryException
                (HttpStatus.NOT_FOUND, "User With NationalId " + nationalId + " not found"));
        userMapper.updateUser(userUpdate, user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    public UserResponse findByNationalId(String nationalId) {
        User user = userRepository.findByNationalId(nationalId).orElseThrow(()->new LibraryException
                (HttpStatus.NOT_FOUND,"User with NationalId " + nationalId+ " not found"));
        return userMapper.toUserResponse(user);
    }
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

}
