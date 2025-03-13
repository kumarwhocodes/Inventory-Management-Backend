package com.minorproject.inventory.exception;

public class TokenNotFound extends RuntimeException {
    public TokenNotFound() {
        super("No Token found in the request");
    }
}