package com.mt.calculator.domain.policy;

import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.Product;

import java.math.BigDecimal;

import static com.mt.calculator.domain.DiscountPolicy.COUNT_BASED;

public class CountBasedDiscountPolicy extends DiscountPolicyCalculator {

    public CountBasedDiscountPolicy(PolicyDetailsProvider policyDetailsProvider) {
        super(policyDetailsProvider);
    }

    @Override
    BigDecimal calculateDiscount(BigDecimal discount, Product product) {
        return discount;
    }

    @Override
    DiscountPolicy supportedPolicy() {
        return COUNT_BASED;
    }
}
