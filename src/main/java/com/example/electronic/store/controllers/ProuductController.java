package com.example.electronic.store.controllers;

import com.example.electronic.store.dtos.ApiResponseMessage;
import com.example.electronic.store.dtos.ProductDto;
import com.example.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProuductController {

    @Autowired
    private ProductService productService;

    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId)
    {
        ProductDto productDto1 = productService.update(productDto, productId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId)
    {
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //getAll
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> deleteProduct()
    {
        List<ProductDto> productDto =productService.getAll();

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
