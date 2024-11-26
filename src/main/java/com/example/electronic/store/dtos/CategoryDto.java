package com.example.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title cannot be less than 4 characters")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "coverImage is required")
    private String coverImage;

}
