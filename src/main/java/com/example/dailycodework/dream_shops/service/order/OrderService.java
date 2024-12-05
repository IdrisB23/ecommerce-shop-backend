package com.example.dailycodework.dream_shops.service.order;

import com.example.dailycodework.dream_shops.enums.OrderStatus;
import com.example.dailycodework.dream_shops.exceptions.ProductOutOfStockException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Cart;
import com.example.dailycodework.dream_shops.model.Order;
import com.example.dailycodework.dream_shops.model.OrderItem;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.repository.OrderRepository;
import com.example.dailycodework.dream_shops.repository.ProductRepository;
import com.example.dailycodework.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order commitedOrder = orderRepository.save(order);
        cartService.clearAndDeleteCart(cart.getId());
        return commitedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        // set the user of the order to the user of the cart
        order.setUser(cart.getUser());
        // set the order status to default state: PENDING
        order.setOrderStatus(OrderStatus.PENDING);
        // set the order date to the current date
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    // calculate the new inventory of the product
                    int newInventory = product.getInventory() - cartItem.getQuantity();
                    // if the product is in stock
                    if (newInventory >= 0) {
                        // update the inventory of products
                        product.setInventory(newInventory);
                        // commit the new inventory to the DB
                        productRepository.save(product);
                        return new OrderItem(
                                order,
                                product,
                                cartItem.getQuantity(),
                                cartItem.getUnitPrice()
                        );
                    }
                    // throw an exception if the product is out of stock
                    else {
                        throw new ProductOutOfStockException(product.getName() + " is out of stock!");
                    }
                })
                .toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No orders for the given user!"));
    }
}
