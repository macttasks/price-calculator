package com.mt.calculator.adapter.api;

import com.mt.calculator.domain.CalculatePriceParameters;
import com.mt.calculator.domain.DiscountPolicy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CalculationRequest(@NotNull UUID productId, @Positive Long quantity, DiscountPolicy policy) {

    public CalculatePriceParameters toDomain() {
        return new CalculatePriceParameters(this.productId, this.quantity, this.policy);
    }
}
