package com.example.softuni_game_store.exeptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String reason) {
        super(reason);
    }
}
