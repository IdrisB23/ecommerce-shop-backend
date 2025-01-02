package com.example.dailycodework.dream_shops;

import static org.junit.jupiter.api.Assertions.*;

import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.service.cart.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Test
    public void testInitializeNewCart() {
        Cart cart = cartService.initializeNewCart(new User(1L));
        assertNotNull(cart);
        assertEquals(1L, cart.getUser().getId());
    }
}