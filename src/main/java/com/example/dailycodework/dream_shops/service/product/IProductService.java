package com.example.dailycodework.dream_shops.service.product;

import com.example.dailycodework.dream_shops.dto.ProductDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceAlreadyExistsException;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.request.AddProductRequest;
import com.example.dailycodework.dream_shops.request.UpdateProductRequest;

import java.util.List;


public interface IProductService {
    Product addProduct(AddProductRequest product) throws ResourceAlreadyExistsException;
    Product getProductById(Long id) throws ResourceNotFoundException;
    void deleteProductById(Long id) throws ResourceNotFoundException;
    Product updateProduct(UpdateProductRequest request, Long productId) throws ResourceNotFoundException;
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category) throws ResourceNotFoundException;
    List<Product> getProductsByBrand(String brand) throws ResourceNotFoundException;
    List<Product> getProductsByCategoryAndBrand(String category, String brand) throws ResourceNotFoundException;
    List<Product> getProductsByName(String name) throws ResourceNotFoundException;
    List<Product> getProductsByBrandAndName(String brand, String name) throws ResourceNotFoundException;
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products) throws ResourceNotFoundException;
}
