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

class PercentageBasedDiscountPolicyTest {

    private final PolicyDetailsProvider detailsProvider = PolicyTestData.build();
    private final PercentageBasedDiscountPolicy policy = new PercentageBasedDiscountPolicy(detailsProvider);

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
                //Arguments.of(new BigDecimal("1.00"), 1L, new BigDecimal("0.90")),
                Arguments.of(new BigDecimal("10.00"), 1L, new BigDecimal("9.00")),
                Arguments.of(new BigDecimal("999.99"), 1L, new BigDecimal("899.99")),
                Arguments.of(new BigDecimal("10.00"), 100L, new BigDecimal("800.00")),
                Arguments.of(new BigDecimal("10.00"), 999L, new BigDecimal("7992.00")),
                Arguments.of(new BigDecimal("10.00"), 1000L, new BigDecimal("5000.00")),
                Arguments.of(new BigDecimal("15.00"), 500L, new BigDecimal("6000.00")),
                Arguments.of(new BigDecimal("8.75"), 150L, new BigDecimal("1050.00")),
                Arguments.of(new BigDecimal("0.00"), 50L, new BigDecimal("0.00")),
                Arguments.of(new BigDecimal("3.33"), 674L, new BigDecimal("1795.54"))
        );

    }
}