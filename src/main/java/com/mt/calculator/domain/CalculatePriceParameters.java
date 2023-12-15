package com.mt.calculator.domain;

import java.util.UUID;

public record CalculatePriceParameters(UUID productId, Long quantity, DiscountPolicy policy) {
}
