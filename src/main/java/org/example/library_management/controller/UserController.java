package org.example.library_management.controller;

import jakarta.validation.Valid;
import org.example.library_management.dto.UserRequest;
import org.example.library_management.dto.UserResponse;
import org.example.library_management.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }
    @GetMapping("/{nationalId}")
    public UserResponse getUser(@PathVariable String nationalId) {
        return userService.findByNationalId(nationalId);
    }
    @PutMapping("/{nationalId}")
    public UserResponse updateUser(@PathVariable String nationalId, @Valid @RequestBody UserRequest userRequest) {
        return userService.update(nationalId, userRequest);
    }
    @GetMapping
    public Page<UserResponse> getUsers(@PageableDefault(size = 10) Pageable pageable ) {
        return userService.findAll(pageable);
    }


}
