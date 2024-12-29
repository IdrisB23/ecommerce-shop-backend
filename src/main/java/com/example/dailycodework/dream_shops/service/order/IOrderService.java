package com.example.dailycodework.dream_shops.service.order;

import com.example.dailycodework.dream_shops.dto.OrderDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId) throws ResourceNotFoundException;

    OrderDto placeOrderAndGetDto(Long userId);

    Order getOrder(Long orderId) throws ResourceNotFoundException;

    OrderDto getOrderDto(Long userId);

    List<Order> getOrdersByUserId(Long userId) throws ResourceNotFoundException;

    List<OrderDto> getOrderDtosByUserId(Long userId);

    OrderDto convertToDto(Order order);
}
