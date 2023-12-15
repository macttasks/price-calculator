package com.mt.calculator.domain.policy;

import com.mt.calculator.PolicyTestData;
import com.mt.calculator.domain.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountBasedDiscountPolicyTest {

    private final PolicyDetailsProvider detailsProvider = PolicyTestData.build();
    private final CountBasedDiscountPolicy policy = new CountBasedDiscountPolicy(detailsProvider);

    @ParameterizedTest
    @MethodSource
    void shouldCorrectlyCalculatePriceAndApplyDiscount(BigDecimal productPrice, Long quantity, BigDecimal expectedPrice) {
        //given
        Product product = new Product(UUID.randomUUID(), productPrice);

        //when
        BigDecimal calculatedPrice = policy.calculate(product, quantity);

        //then
        assertEquals(expectedPrice, calculatedPrice);
    }

    private static Stream<Arguments> shouldCorrectlyCalculatePriceAndApplyDiscount() {
        return Stream.of(
                Arguments.of(new BigDecimal("10"), 99L, new BigDecimal("990.00")),
                Arguments.of(new BigDecimal("10"), 100L, new BigDecimal("900.00")),
                Arguments.of(new BigDecimal("10"), 999L, new BigDecimal("8991.00")),
                Arguments.of(new BigDecimal("10"), 1000L, new BigDecimal("5000.00")),
                Arguments.of(new BigDecimal("5.50"), 99L, new BigDecimal("544.50")),
                Arguments.of(new BigDecimal("5.50"), 100L, new BigDecimal("450.00")),
                Arguments.of(new BigDecimal("5.50"), 999L, new BigDecimal("4495.50")),
                Arguments.of(new BigDecimal("5.50"), 1000L, new BigDecimal("500.00")),

                Arguments.of(new BigDecimal("1.00"), 1L, new BigDecimal("1.00")),
                Arguments.of(new BigDecimal("999.99"), 1L, new BigDecimal("999.99")),
                Arguments.of(new BigDecimal("10.00"), 100L, new BigDecimal("900.00")),
                Arguments.of(new BigDecimal("10.00"), 1000L, new BigDecimal("5000.00")),
                Arguments.of(new BigDecimal("10.00"), 1001L, new BigDecimal("5005.00")),
                Arguments.of(new BigDecimal("15.00"), 500L, new BigDecimal("7000.00")),
                Arguments.of(new BigDecimal("8.75"), 150L, new BigDecimal("1162.50")),
                Arguments.of(new BigDecimal("0.00"), 50L, new BigDecimal("0.00"))
        );

    }
}