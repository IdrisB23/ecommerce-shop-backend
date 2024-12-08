package com.example.dailycodework.dream_shops.repository;

import com.example.dailycodework.dream_shops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  void deleteAllByCartId(Long id);
    Optional<List<CartItem>> findByCartId(Long cartId);
}