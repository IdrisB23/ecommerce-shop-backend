package com.example.dailycodework.dream_shops.request;

import com.example.dailycodework.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

/*
* Not advised to work with the model Product.java directly. May run into a problem
*/
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
