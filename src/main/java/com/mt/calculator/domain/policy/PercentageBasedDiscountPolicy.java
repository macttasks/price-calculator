package com.mt.calculator.domain.policy;

import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.Product;

import java.math.BigDecimal;

import static com.mt.calculator.domain.DiscountPolicy.PERCENTAGE_BASED;

public class PercentageBasedDiscountPolicy extends DiscountPolicyCalculator {

    public PercentageBasedDiscountPolicy(PolicyDetailsProvider policyDetailsProvider) {
        super(policyDetailsProvider);
    }

    @Override
    BigDecimal calculateDiscount(BigDecimal discount, Product product) {
        return discount.multiply(product.price());
    }

    @Override
    DiscountPolicy supportedPolicy() {
        return PERCENTAGE_BASED;
    }

}
