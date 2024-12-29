package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.dto.CartDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long cartId);

    CartDto getCartDtoById(Long cartId);

    Cart getCartByUserId(Long userId) throws ResourceNotFoundException;

    CartDto getCartDtoByUserId(Long userId);

    void clearAndDeleteCart(Long cartId) throws ResourceNotFoundException;
    BigDecimal getTotalPrice(Long cartId) throws ResourceNotFoundException;
    Cart initializeNewCart(User user) throws ResourceNotFoundException;

    CartDto convertToDto(Cart cart);
}
