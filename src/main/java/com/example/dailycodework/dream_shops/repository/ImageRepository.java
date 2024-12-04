package com.example.dailycodework.dream_shops.repository;

import com.example.dailycodework.dream_shops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<List<Image>> findByProductId(Long id);
}