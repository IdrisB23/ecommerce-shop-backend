package com.example.dailycodework.dream_shops.service.product;

import com.example.dailycodework.dream_shops.dto.ProductDto;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.request.AddProductRequest;
import com.example.dailycodework.dream_shops.request.UpdateProductRequest;

import java.util.List;


public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
