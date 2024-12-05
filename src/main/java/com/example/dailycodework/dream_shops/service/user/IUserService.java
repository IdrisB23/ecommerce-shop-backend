package com.example.dailycodework.dream_shops.service.user;

import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.request.CreateUserRequest;
import com.example.dailycodework.dream_shops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id);
    void deleteUser(Long userId);
    User createNewUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
}
