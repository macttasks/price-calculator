package com.mt.calculator.domain;

import com.mt.calculator.PolicyTestData;
import com.mt.calculator.domain.policy.CountBasedDiscountPolicy;
import com.mt.calculator.domain.policy.DiscountPolicyCalculator;
import com.mt.calculator.domain.policy.PercentageBasedDiscountPolicy;
import com.mt.calculator.domain.policy.PolicyDetailsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PricingServiceTest {

    private final PolicyDetailsProvider detailsProvider = PolicyTestData.build();

    private final List<DiscountPolicyCalculator> policies = List.of(new CountBasedDiscountPolicy(detailsProvider), new PercentageBasedDiscountPolicy(detailsProvider));
    private final ProductRepository productRepository = new InMemoryProductRepository();

    private final PricingService pricingService = new PricingService(policies, productRepository);

    @ParameterizedTest
    @MethodSource
    void shouldPickCorrectPolicyAndCalculatePrice(BigDecimal productPrice, Long quantity, DiscountPolicy policy, BigDecimal expectedPrice) {
        //given
        Product product = new Product(UUID.randomUUID(), productPrice);
        productRepository.save(product);

        //and
        CalculatePriceParameters parameters = new CalculatePriceParameters(product.id(), quantity, policy);

        //when
        BigDecimal calculatedPrice = pricingService.calculatePrice(parameters);

        //then
        assertEquals(expectedPrice, calculatedPrice);
    }

    private static Stream<Arguments> shouldPickCorrectPolicyAndCalculatePrice() {
        return Stream.of(
                Arguments.of(new BigDecimal("10"), 999L, DiscountPolicy.COUNT_BASED, new BigDecimal("8991.00")),
                Arguments.of(new BigDecimal("10"), 999L, DiscountPolicy.PERCENTAGE_BASED, new BigDecimal("7992.00")));
    }

    @Test
    void shouldThrowExceptionIfPolicyDoesNotExist() {
        //given
        Product product = new Product(UUID.randomUUID(), new BigDecimal("10"));
        productRepository.save(product);

        //and
        PricingService pricingServiceWithoutPolicies = new PricingService(List.of(), productRepository);

        //and
        CalculatePriceParameters parameters = new CalculatePriceParameters(product.id(), 1L, DiscountPolicy.COUNT_BASED);

        //expect
        assertThrows(IncorrectPolicyException.class, () -> pricingServiceWithoutPolicies.calculatePrice(parameters));
    }

    @Test
    void shouldThrowExceptionIfProductDoesNotExist() {
        //given
        CalculatePriceParameters parameters = new CalculatePriceParameters(UUID.randomUUID(), 1L, DiscountPolicy.COUNT_BASED);

        //expect
        assertThrows(UnknownProductException.class, () -> pricingService.calculatePrice(parameters));
    }
}