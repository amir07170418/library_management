package org.example.library_management.service;

import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.dto.UserUpdate;
import org.example.library_management.exception.LibraryException;
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
import static org.mockito.Mockito.*;

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
    void save_whenNationalIdExists() {
        UserRequest userRequest = new UserRequest("Hossein", "Ahmadi", "09955654785"
                , "6880174523");

        when(userRepository.existsByNationalId("6880174523")).thenReturn(true);

        assertThrows(LibraryException.class, () -> userService.save(userRequest));

        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).toUser(userRequest);
    }

    @Test
    void update() {
        User user = new User(1L,"Hossein","Ahmadi","09955654785"
                ,"6880174523",null);
        UserUpdate userUpdate =new UserUpdate("Hossein","Akbari","09955654785");
        UserResponse userResponse = new UserResponse(1L,"Hossein","Akbari","09955654785"
                ,"6880174523");
        when(userRepository.findByNationalId("6880174523")).thenReturn(Optional.of(user));
        when(userMapper.updateUser(userUpdate,user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result=userService.update("6880174523",userUpdate);
        assertEquals(userResponse,result);

        verify(userRepository).findByNationalId("6880174523");
        verify(userRepository).save(user);
    }

    @Test
    void update_whenUserNotFound() {
        UserUpdate userUpdate = new UserUpdate("Hossein", "Akbari", "09955654785");

        when(userRepository.findByNationalId("6880174523")).thenReturn(Optional.empty());

        assertThrows(LibraryException.class,
                () -> userService.update("6880174523", userUpdate));

        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).updateUser(any(UserUpdate.class), any(User.class));
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

    @Test
    void findByNationalId_whenUserNotFound() {
        when(userRepository.findByNationalId("6880174523")).thenReturn(Optional.empty());

        assertThrows(LibraryException.class,
                () -> userService.findByNationalId("6880174523"));

        verify(userRepository).findByNationalId("6880174523");
        verify(userMapper, never()).toUserResponse(any(User.class));
    }
}