package com.mt.calculator;

import com.mt.calculator.domain.IncorrectPolicyException;
import com.mt.calculator.domain.UnknownProductException;
import com.mt.calculator.domain.policy.InvalidRangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CalculationExceptionHandler {

    @ExceptionHandler({
            InvalidRangeException.class,
            IncorrectPolicyException.class,
            UnknownProductException.class,
            InvalidConfigurationException.class
    })
    public ResponseEntity<String> handle(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
