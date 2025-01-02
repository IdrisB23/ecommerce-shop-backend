package com.example.dailycodework.dream_shops;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.repository.CartRepository;
import com.example.dailycodework.dream_shops.service.cart.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceUnitTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testGetCartById() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCartById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}