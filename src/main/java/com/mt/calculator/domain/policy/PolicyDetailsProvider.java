package com.mt.calculator.domain.policy;

import com.mt.calculator.domain.DiscountPolicy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PolicyDetailsProvider {

    Optional<List<Range>> getSpecification(DiscountPolicy policy);

    Map<DiscountPolicy, List<Range>> getConfiguration();


}
