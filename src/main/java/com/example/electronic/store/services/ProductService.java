package com.example.electronic.store.services;

import com.example.electronic.store.dtos.ProductDto;
import com.example.electronic.store.repositories.ProductRepository;

import java.util.List;

public interface ProductService {

    // create
    ProductDto create(ProductDto productDto);

    // update
    ProductDto update(ProductDto productDto, String productId);

    // delete
    void delete(String productId);

    //getAll
    List<ProductDto> getAll();

    // create product with category

    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //update category

    ProductDto updateCategory(String productId, String categoryId);
}
