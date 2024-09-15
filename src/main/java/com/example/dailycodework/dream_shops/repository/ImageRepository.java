package com.example.dailycodework.dream_shops.repository;

import com.example.dailycodework.dream_shops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}