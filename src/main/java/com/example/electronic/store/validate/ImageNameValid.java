package com.example.electronic.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    String message() default "Image not valid !!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
