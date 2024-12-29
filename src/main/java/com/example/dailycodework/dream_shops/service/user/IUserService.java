package com.example.dailycodework.dream_shops.service.user;

import com.example.dailycodework.dream_shops.dto.UserDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.request.CreateUserRequest;
import com.example.dailycodework.dream_shops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id) throws ResourceNotFoundException;

    UserDto getUserDtoById(Long id);

    void deleteUser(Long userId) throws ResourceNotFoundException;
    User createNewUser(CreateUserRequest request) throws ResourceAlreadyExistsException;

    UserDto createNewUserAndReturnDto(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Long userId) throws ResourceNotFoundException;

    UserDto updateUserAndReturnDto(UpdateUserRequest request, Long userId);

    UserDto convertToDto(User user);
}
