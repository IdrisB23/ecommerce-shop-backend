package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.dto.CartDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.User;
import com.example.dailycodework.dream_shops.repository.CartItemRepository;
import com.example.dailycodework.dream_shops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final ModelMapper modelMapper;

    @Override
    public Cart getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cart;
    }


    @Override
    public CartDto getCartDtoById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return convertToDto(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }

    @Override
    public CartDto getCartDtoByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return convertToDto(cart);
    }

    @Override
    public void clearAndDeleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        cart.getCartItems().forEach(cartItem -> {
            // set the cartItem's cart to null to avoid a constraint violation
            cartItem.setCart(null);
            // commit changes / save the cartItem to update the database
            cartItemRepository.save(cartItem);
        });
        cartItemRepository.deleteAllByCartId(cartId);
        cart.getCartItems().clear();
        cartRepository.deleteById(cartId);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(user.getId());
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        } else {
            Cart cart = new Cart();
            cart.setId(cartIdGenerator.incrementAndGet());
            cart.setUser(user);
            return cartRepository.save(cart);
        }
    }

    @Override
    public CartDto convertToDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }
}
