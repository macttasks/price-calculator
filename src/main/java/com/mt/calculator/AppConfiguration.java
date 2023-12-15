package com.mt.calculator;

import com.mt.calculator.domain.InMemoryProductRepository;
import com.mt.calculator.domain.PricingService;
import com.mt.calculator.domain.ProductRepository;
import com.mt.calculator.domain.policy.CountBasedDiscountPolicy;
import com.mt.calculator.domain.policy.DiscountPolicyCalculator;
import com.mt.calculator.domain.policy.PercentageBasedDiscountPolicy;
import com.mt.calculator.domain.policy.PolicyDetailsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfiguration {

    @Bean
    public List<DiscountPolicyCalculator> priceCalculators(PolicyDetailsProvider policyDetailsProvider) {
        return List.of(new CountBasedDiscountPolicy(policyDetailsProvider), new PercentageBasedDiscountPolicy(policyDetailsProvider));
    }

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    public PricingService pricingService(List<DiscountPolicyCalculator> priceCalculators, ProductRepository productRepository) {
        return new PricingService(priceCalculators, productRepository);
    }
}
