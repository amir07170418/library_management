package org.example.library_management.service;

import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.mapper.UserMapper;
import org.example.library_management.model.User;
import org.example.library_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;
    @Test
    void save() {
        User user = new User(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523",null);
        UserRequest userRequest = new UserRequest("Hossein","Ahmadi","09955654785"
                ,"6880174523");
        UserResponse userResponse = new UserResponse(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523");
        when(userRepository.existsByNationalId("6880174523")).thenReturn(false);
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result= userService.save(userRequest);

        assertEquals(userResponse,result);
        verify(userRepository).save(user);
    }

    @Test
    void update() {
        User user = new User(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523",null);
        UserRequest userRequest = new UserRequest("Hossein","Akbari","09955654785"
                ,"6880174523");
        UserResponse userResponse = new UserResponse(1L,"Hossein","Akbari","09955654785"
                ,"6880174523");
        when(userRepository.findByNationalId("6880174523")).thenReturn(Optional.of(user));
        when(userMapper.updateUser(userRequest,user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result=userService.update("6880174523",userRequest);
        assertEquals(userResponse,result);

        verify(userRepository).findByNationalId("6880174523");
        verify(userRepository).save(user);
    }

    @Test
    void findByNationalId() {
        User user = new User(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523",null);
        UserResponse userResponse = new UserResponse(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523");
        when(userRepository.findByNationalId("6880174523")).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.findByNationalId("6880174523");
        assertEquals(userResponse,result);
        verify(userRepository).findByNationalId("6880174523");
    }
}