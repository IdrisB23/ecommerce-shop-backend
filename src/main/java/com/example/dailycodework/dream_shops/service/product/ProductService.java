package com.example.dailycodework.dream_shops.service.product;

import com.example.dailycodework.dream_shops.dto.ImageDto;
import com.example.dailycodework.dream_shops.dto.ProductDto;
import com.example.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dailycodework.dream_shops.model.Category;
import com.example.dailycodework.dream_shops.model.Image;
import com.example.dailycodework.dream_shops.model.Product;
import com.example.dailycodework.dream_shops.repository.CategoryRepository;
import com.example.dailycodework.dream_shops.repository.ImageRepository;
import com.example.dailycodework.dream_shops.repository.ProductRepository;
import com.example.dailycodework.dream_shops.request.AddProductRequest;
import com.example.dailycodework.dream_shops.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        // All of this is done via Optional-Supplier
        // check if the category is found in the database
        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(()->{
                    // if not, save it as a new category
                    Category newCategory = new Category(request.getCategory().getName(), request.getCategory().getDescription());
                    categoryRepository.save(newCategory);
                    return newCategory;
                });
        // set it as the new product category
        Product newProduct = createProduct(request, category);
        productRepository.save(newProduct);
        return newProduct;
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new ResourceNotFoundException("Product not found!");
                });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setPrice(request.getPrice());
        Category category = categoryRepository
                .findByName(request.getCategory().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }

    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository
                .findByProductId(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Images not found!"));
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
