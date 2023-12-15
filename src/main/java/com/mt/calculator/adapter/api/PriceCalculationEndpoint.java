package com.mt.calculator.adapter.api;

import com.mt.calculator.domain.PricingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate-price")
public class PriceCalculationEndpoint {

    private final PricingService pricingService;

    public PriceCalculationEndpoint(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping
    public CalculationResponse calculatePrice(@RequestBody @Valid CalculationRequest request) {
        BigDecimal calculatedPrice = pricingService.calculatePrice(request.toDomain());
        return new CalculationResponse(calculatedPrice);

    }
}
