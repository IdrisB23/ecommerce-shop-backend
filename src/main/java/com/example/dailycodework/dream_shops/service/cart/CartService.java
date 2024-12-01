package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.repository.CartItemRepository;
import com.example.dailycodework.dream_shops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        // TODO (test corr. Controller; remove/add lines):
        // 2 lines below needed to initiate update to the cart's total amount?
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearAndDeleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        cartItemRepository.deleteAllByCartId(cartId);
        cart.getCartItems().clear();
        // TODO: setting total amount to ZERO here is needed or is it not because we are deleting the cart?
        // cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.deleteById(cartId);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cart.getTotalAmount();
    }

}
