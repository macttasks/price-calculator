package com.mt.calculator;

import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.policy.PolicyDetailsProvider;
import com.mt.calculator.domain.policy.Range;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Component
@ConditionalOnProperty(value = "calculation-policies.validate", havingValue = "true")
public class PropertiesValidator implements ApplicationRunner {

    private final PolicyDetailsProvider policyDetailsProvider;

    public PropertiesValidator(PolicyDetailsProvider policyDetailsProvider) {
        this.policyDetailsProvider = policyDetailsProvider;
    }

    @Override
    public void run(ApplicationArguments args) {
        validate(policyDetailsProvider);
    }

    private void validate(PolicyDetailsProvider policyDetailsProvider) {
        validateKeys(policyDetailsProvider);
        policyDetailsProvider.getConfiguration().values().forEach(this::validateContinuity);
    }

    private void validateKeys(PolicyDetailsProvider policyDetailsProvider) {
        if (!policyDetailsProvider.getConfiguration().keySet().equals(new HashSet<>(Arrays.asList(DiscountPolicy.values())))) {
            throw new InvalidConfigurationException("Discount Policy key mismatch with configuration");
        }
    }

    private void validateContinuity(List<Range> ranges) {
        List<Range> clone = new ArrayList<>(ranges);

        clone.sort(Comparator.comparing(Range::from));

        for (int i = 0; i < clone.size() - 1; i++) {
            if (clone.get(i).toExclusive().compareTo(clone.get(i + 1).from()) != 0) {
                throw new InvalidConfigurationException("Following configuration has discontinued ranges " + clone);
            }
        }
    }
}
