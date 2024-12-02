package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long cartId);
    void clearAndDeleteCart(Long cartId);
    BigDecimal getTotalPrice(Long cartId);

    Long initializeNewCart();
}
