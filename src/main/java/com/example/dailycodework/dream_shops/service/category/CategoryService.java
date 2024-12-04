package com.example.dailycodework.dream_shops.service.category;

import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Category;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.repository.CategoryRepository;
import com.example.dailycodework.dream_shops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category addCategory(Category category) {
        // below line is bad code, because it couples our code with the type of attribute category.name
        // if the type changes in the future, this snippet breaks.
        // String category_name = category.getName();
        return Optional.of(category).filter(c-> !categoryRepository.existsByName(category.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(()-> new ResourceAlreadyExistsException("Category with name" + category.getName() + "already exists!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            oldCategory.setDescription(category.getDescription());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteAllCategories() {
        List<Category> allCategories = this.getAllCategories();
        for (Category category : allCategories) {
            this.deleteCategoryById(category.getId());
        }
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        List<Product> products = category.getProducts();   // get all products in the category
        for (Product product : products) {
            product.setCategory(null);  // set the category of the product to null
            productRepository.save(product);  // save the product
        }
        categoryRepository.delete(category);
    }
}
