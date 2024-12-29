package com.example.dailycodework.dream_shops.controller;


import com.example.dailycodework.dream_shops.dto.CartDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.response.ApiResponse;
import com.example.dailycodework.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId) {
        try {
            CartDto cartDto = cartService.getCartDtoById(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart fetched successfully", cartDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/clear-and-delete/{cartId}")
    public ResponseEntity<ApiResponse> clearAndDeleteCartById(@PathVariable Long cartId) {
        try {
            cartService.clearAndDeleteCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared and deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/cart/total-amount/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    "Total amount for cart fetched successfully", cartService.getTotalPrice(cartId)
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
