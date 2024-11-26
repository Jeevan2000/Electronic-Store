package com.example.electronic.store.services.impl;

import com.example.electronic.store.dtos.ProductDto;
import com.example.electronic.store.entities.Category;
import com.example.electronic.store.entities.Product;
import com.example.electronic.store.exceptions.ResourceNotFoundException;
import com.example.electronic.store.repositories.CategoryRepository;
import com.example.electronic.store.repositories.ProductRepository;
import com.example.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto) {
        String uid = UUID.randomUUID().toString();
        productDto.setProductId(uid);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setPrice(productDto.getPrice());
        product.setAddedDate(productDto.getAddedDate());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setDescription(productDto.getDescription());
        product.setProductId(productId);

        Product updateproduct = productRepository.save(product);
        return mapper.map(updateproduct, ProductDto.class);

    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtosList = products.stream().map(p1 -> mapper.map(p1, ProductDto.class)).collect(Collectors.toList());
        return productDtosList;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        // fetch the category
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Does not exits"));

        String uid = UUID.randomUUID().toString();
        productDto.setProductId(uid);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);


    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
      Product product =  productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Proudct not found !!!"));
      Category category =  categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!!"));

      product.setCategory(category);

      Product savedProduct =productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }
}
