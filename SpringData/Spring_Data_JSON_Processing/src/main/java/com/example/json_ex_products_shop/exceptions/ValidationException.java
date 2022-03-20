package com.example.json_ex_products_shop.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String reason) {
        super(reason);
    }
}
