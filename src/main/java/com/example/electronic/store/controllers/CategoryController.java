package com.example.electronic.store.controllers;

import com.example.electronic.store.dtos.ApiResponseMessage;
import com.example.electronic.store.dtos.CategoryDto;
import com.example.electronic.store.dtos.ProductDto;
import com.example.electronic.store.services.CategoryService;
import com.example.electronic.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return  new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId)
    {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return  new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId)
    {
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //getsingle
    @GetMapping("/single/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId)
    {
        CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
    }

    // create product with category
    @PostMapping("/{catgoryId}/products")
    public ResponseEntity<ProductDto> createProudctWithCatgory(@PathVariable("catgoryId") String catgoryId, @RequestBody ProductDto dto)
    {
        ProductDto productDto = productService.createWithCategory(dto, catgoryId);
        return  new ResponseEntity<>(productDto, HttpStatus.CREATED);

    }

    //update category of product
    @PutMapping("/{catgoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryFromProduct(@PathVariable("catgoryId") String catgoryId, @PathVariable("productId") String productId)
    {
        ProductDto productDto = productService.updateCategory(productId, catgoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }


}
