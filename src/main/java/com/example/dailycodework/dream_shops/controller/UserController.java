package com.example.dailycodework.dream_shops.controller;

import com.example.dailycodework.dream_shops.dto.UserDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.request.CreateUserRequest;
import com.example.dailycodework.dream_shops.request.UpdateUserRequest;
import com.example.dailycodework.dream_shops.response.ApiResponse;
import com.example.dailycodework.dream_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            UserDto user = userService.getUserDtoById(userId);
            return ResponseEntity.ok(new ApiResponse("Success", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            UserDto user = userService.createNewUserAndReturnDto(request);
            return ResponseEntity.ok(new ApiResponse("Create User Success!", user));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            UserDto user = userService.updateUserAndReturnDto(request, userId);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
