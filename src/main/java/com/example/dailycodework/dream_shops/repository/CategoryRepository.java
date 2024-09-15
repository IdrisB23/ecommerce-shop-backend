package com.example.dailycodework.dream_shops.repository;

import com.example.dailycodework.dream_shops.model.Category;
import com.example.dailycodework.dream_shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}