package com.example.dailycodework.dream_shops.service.category;

import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id) throws ResourceNotFoundException;
    List<Category> getAllCategories();
    Category getCategoryByName(String name) throws ResourceNotFoundException;
    Category addCategory(Category category) throws ResourceAlreadyExistsException;
    Category updateCategory(Category category, Long id) throws ResourceNotFoundException;
    void deleteAllCategories();
    void deleteCategoryById(Long id) throws ResourceNotFoundException;
}
