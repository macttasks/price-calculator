package com.mt.calculator.domain;

public class IncorrectPolicyException extends RuntimeException {

    public IncorrectPolicyException(String message) {
        super(message);
    }
}
