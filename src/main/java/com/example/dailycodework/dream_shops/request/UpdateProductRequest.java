package com.example.dailycodework.dream_shops.request;

import com.example.dailycodework.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
