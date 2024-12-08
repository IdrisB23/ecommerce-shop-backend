package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.CartItem;

import java.util.List;


// TODO: use DTOs instead of entities in the service layer
public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity) throws ResourceNotFoundException;
    void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException;
    void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException;
    CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) throws ResourceNotFoundException;
    List<CartItem> getCartItemsByCartId(Long cartId);
}
