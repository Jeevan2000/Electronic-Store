package com.example.electronic.store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public void initialize(ImageNameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        logger.info("Message from isValid : {} ", s);

        if(s.isBlank())
        {
            return  false;
        }
        else
        {
            return true;
        }
    }
}
