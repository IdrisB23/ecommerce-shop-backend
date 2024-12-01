package com.example.dailycodework.dream_shops.repository;

import com.example.dailycodework.dream_shops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  void deleteAllByCartId(Long id);
}