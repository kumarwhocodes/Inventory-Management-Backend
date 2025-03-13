package com.minorproject.inventory.exception;

public class FirebaseOperationException extends RuntimeException {
    public FirebaseOperationException(String message) {
        super(message);
    }
}