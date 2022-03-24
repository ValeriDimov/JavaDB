package com.example.xml_product_shop.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String reason) {
        super(reason);
    }
}
