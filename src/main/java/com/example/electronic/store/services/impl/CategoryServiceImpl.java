package com.example.electronic.store.services.impl;

import com.example.electronic.store.dtos.CategoryDto;
import com.example.electronic.store.entities.Category;
import com.example.electronic.store.exceptions.ResourceNotFoundException;
import com.example.electronic.store.repositories.CategoryRepository;
import com.example.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return  mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!!"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        categoryRepository.save(category);
        return  categoryDto;
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!!"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!!"));
        return mapper.map(category, CategoryDto.class);
    }
}
