package com.example.dailycodework.dream_shops.controller;


import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.response.ApiResponse;
import com.example.dailycodework.dream_shops.service.cart.CartService;
import com.example.dailycodework.dream_shops.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


// TODO: use DTOs instead of entities in the controller layer
@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final CartService cartService;

    @GetMapping("by/cartId/{cartId}")
    public ResponseEntity<ApiResponse> getCartItemsByCartId(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    "Cart items fetched successfully", cartItemService.getCartItemsByCartId(cartId)
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("cart-item/add")
    // difference between using int and Integer is (among others) their default values (0 vs. null)
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        if (cartId == null) {
            cartId = cartService.initializeNewCart();
        }
        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse(
                    "Item added to cart successfully", null
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("cart-item/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam(required = false) Long cartId,
                                                          @RequestParam Long productId) {
        if (cartId == null) {
            cartId = cartService.initializeNewCart();
        }
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse(
                    "Item removed from cart successfully", null
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("cart-item/update")
    public ResponseEntity<ApiResponse> updateItemInCart(@RequestParam(required = false) Long cartId,
                                                        @RequestParam Long productId,
                                                        @RequestParam Integer quantity) {
        if (cartId == null) {
            cartId = cartService.initializeNewCart();
        }
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse(
                    "Item updated in cart successfully", null
            ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
