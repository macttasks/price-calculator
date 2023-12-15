package com.mt.calculator.adapter;

import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.policy.PolicyDetailsProvider;
import com.mt.calculator.domain.policy.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ConfigurationProperties("calculation-policies")
public record PolicyConfigurationProvider(
        Map<DiscountPolicy, List<Range>> entries) implements PolicyDetailsProvider {

    @Override
    public Optional<List<Range>> getSpecification(DiscountPolicy policy) {
        return Optional.ofNullable(entries.get(policy));
    }

    @Override
    public Map<DiscountPolicy, List<Range>> getConfiguration() {
        return entries;
    }
}
