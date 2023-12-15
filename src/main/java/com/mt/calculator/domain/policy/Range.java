package com.mt.calculator.domain.policy;

import java.math.BigDecimal;

public record Range(
        Long from,
        Long toExclusive,
        BigDecimal discount) {
}
