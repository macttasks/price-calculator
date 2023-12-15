package com.mt.calculator.domain;

import com.mt.calculator.domain.policy.DiscountPolicyCalculator;

import java.math.BigDecimal;
import java.util.List;


public class PricingService {

    private final List<DiscountPolicyCalculator> priceCalculators;
    private final ProductRepository productRepository;

    public PricingService(List<DiscountPolicyCalculator> priceCalculators, ProductRepository productRepository) {
        this.priceCalculators = priceCalculators;
        this.productRepository = productRepository;
    }

    public BigDecimal calculatePrice(CalculatePriceParameters parameters) {
        return productRepository.findById(parameters.productId())
                .map(product -> calculatePrice(product, parameters))
                .orElseThrow(() -> new UnknownProductException("Could not find a product with id " + parameters.productId()));
    }

    private BigDecimal calculatePrice(Product product, CalculatePriceParameters parameters) {
        return priceCalculators.stream()
                .filter(calculator -> calculator.applies(parameters))
                .findFirst()
                .map(calculator -> calculator.calculate(product, parameters.quantity()))
                .orElseThrow(() -> new IncorrectPolicyException("Could not find a valid policy for type " + parameters.policy()));
    }
}
