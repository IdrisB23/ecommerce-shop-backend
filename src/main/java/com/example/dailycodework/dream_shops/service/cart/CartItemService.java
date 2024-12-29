package com.example.dailycodework.dream_shops.service.cart;

import com.example.dailycodework.dream_shops.dto.CartItemDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.CartItem;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.repository.CartItemRepository;
import com.example.dailycodework.dream_shops.repository.CartRepository;
import com.example.dailycodework.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


// TODO: use DTOs instead of entities in the service layer
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ICartService cartService;
    private final IProductService productService;
    private final ModelMapper modelMapper;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. get the Cart
        Cart cart = cartService.getCartById(cartId);
        // 2. get the Product
        Product product = productService.getProductById(productId);
        // 3. check if the product is already in the cart
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem()); // creating a new instance does not generate an id (persisting it does)
        // 4. if the product is not in the cart, add the product to the cart with the requested quantity
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        // 5. if the product is already in the cart, increase the quantity
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        // 6. finally...
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        cart.addCartItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // 1. get the cart
        Cart cart = cartService.getCartById(cartId);
        // 2. iterate through the cartItems in the cart and remove the item with the given productId
        CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        // 1. get the cart
        Cart cart = cartService.getCartById(cartId);
        // 2. iterate through the cartItems in the cart and update the quantity of the item with the given productId
        CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);
        cartItem.setQuantity(quantity);
        // The line below is not needed, because we are updating the quantity and not the product itself
        // cartItem.setUnitPrice(productService.getProductById(productId).getPrice());
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItemDto getCartItemDtoByCartIdAndProductId(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in the cart!"));
        return convertToDto(cartItem);
    }

    @Override
    public CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in the cart!"));
    }

    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }

    @Override
    public List<CartItemDto> getCartItemDtosByCartId(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cartItems
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public CartItemDto convertToDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDto.class);
    }
}
