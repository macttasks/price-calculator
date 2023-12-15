package com.mt.calculator.domain;

public class UnknownProductException extends RuntimeException {
    public UnknownProductException(String message) {
        super(message);
    }
}
