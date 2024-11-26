package com.example.electronic.store.repositories;

import com.example.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByTitle(String subTitle);
}
