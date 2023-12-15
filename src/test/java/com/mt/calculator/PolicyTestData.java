package com.mt.calculator;

import com.mt.calculator.adapter.PolicyConfigurationProvider;
import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.policy.Range;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PolicyTestData {

    public static final List<Range> countBasedRanges = List.of(
            new Range(0L, 100L, BigDecimal.ZERO),
            new Range(100L, 1000L, BigDecimal.ONE),
            new Range(1000L, null, new BigDecimal("5")
            ));

    public static final List<Range> percentageBasedRanges = List.of(
            new Range(0L, 100L, new BigDecimal("0.1")),
            new Range(100L, 1000L, new BigDecimal("0.2")),
            new Range(1000L, null, new BigDecimal("0.5")
            ));

    public static PolicyConfigurationProvider build() {
        Map<DiscountPolicy, List<Range>> map = Map.of(DiscountPolicy.COUNT_BASED, countBasedRanges, DiscountPolicy.PERCENTAGE_BASED, percentageBasedRanges);
        return new PolicyConfigurationProvider(map);
    }


}
