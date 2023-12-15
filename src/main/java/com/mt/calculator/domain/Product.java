package com.mt.calculator.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, BigDecimal price) {
}
