package com.example.dailycodework.dream_shops.service.user;

import com.example.dailycodework.dream_shops.dto.UserDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.repository.CartRepository;
import com.example.dailycodework.dream_shops.repository.UserRepository;
import com.example.dailycodework.dream_shops.request.CreateUserRequest;
import com.example.dailycodework.dream_shops.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        return convertToDto(getUserById(id));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found");
                });
    }

    @Override
    public User createNewUser(CreateUserRequest request) {
        return Optional.of(request)
                // here, filter returns Optional(empty), if the user exists.
                .filter(createUserRequest -> !userRepository.existsByEmail(request.getEmail()))
                // Otherwise, it returns Optional(of(request))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    User savedUser = userRepository.save(user);
                    // every new User is instantiated with an empty cart
                    Cart cart = new Cart();
                    cart.setUser(savedUser);
                    Cart savedCart = cartRepository.save(cart);
                    user.setCart(savedCart);
                    return userRepository.save(savedUser);
                })
                .orElseThrow(() -> new ResourceAlreadyExistsException(
                        "User with email:: " + request.getEmail() + " already exists"));
    }

    @Override
    public UserDto createNewUserAndReturnDto(CreateUserRequest request) {
        return convertToDto(createNewUser(request));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto updateUserAndReturnDto(UpdateUserRequest request, Long userId) {
        return convertToDto(updateUser(request, userId));
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
