package com.example.electronic.store.services;

import com.example.electronic.store.dtos.CategoryDto;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, String catId);

    //delete
    void deleteCategory(String categoryId);

    //get single category detail
    CategoryDto getSingleCategory(String categoryId);
}
