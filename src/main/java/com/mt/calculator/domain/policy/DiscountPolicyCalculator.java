package com.mt.calculator.domain.policy;

import com.mt.calculator.InvalidConfigurationException;
import com.mt.calculator.domain.CalculatePriceParameters;
import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class DiscountPolicyCalculator {

    private final PolicyDetailsProvider policyDetailsProvider;

    protected DiscountPolicyCalculator(PolicyDetailsProvider policyDetailsProvider) {
        this.policyDetailsProvider = policyDetailsProvider;
    }

    abstract BigDecimal calculateDiscount(BigDecimal discount, Product product);

    abstract DiscountPolicy supportedPolicy();

    public boolean applies(CalculatePriceParameters parameters) {
        return supportedPolicy() == parameters.policy();
    }

    public BigDecimal calculate(Product product, Long quantity) {
        return getPolicy()
                .stream()
                .filter(range -> isValueInRange(quantity, range))
                .findFirst()
                .map(Range::discount)
                .map(discount -> calculateWithDiscount(discount, product, quantity))
                .orElseThrow(() -> new InvalidRangeException("Error calculating price for product " + product.id()));
    }

    private BigDecimal calculateWithDiscount(BigDecimal discount, Product product, Long quantity) {
        return product
                .price()
                .subtract(calculateDiscount(discount, product))
                .max(BigDecimal.ZERO)
                .multiply(new BigDecimal(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }


    private List<Range> getPolicy() {
        return policyDetailsProvider.getSpecification(supportedPolicy()).orElseThrow(() -> new InvalidConfigurationException("Cannot find specification for policy " + supportedPolicy()));
    }

    private boolean isValueInRange(Long quantity, Range range) {
        return quantity >= range.from() && (range.toExclusive() == null || quantity < range.toExclusive());
    }
}
