package com.example.dailycodework.dream_shops.service.order;

import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId) throws ResourceNotFoundException;
    Order getOrder(Long orderId) throws ResourceNotFoundException;
    List<Order> getOrdersByUserId(Long userId) throws ResourceNotFoundException;
}
