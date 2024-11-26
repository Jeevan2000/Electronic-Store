package com.example.electronic.store.dtos;

import com.example.electronic.store.entities.Category;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productId;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private Date addedDate;

    private boolean live;
    private boolean stock;

    private CategoryDto category;
}
