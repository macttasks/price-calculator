package com.mt.calculator;

import com.mt.calculator.domain.Product;
import com.mt.calculator.domain.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "generate-data", havingValue = "true")
public class ProductInitializer implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(ProductInitializer.class);

    private final ProductRepository productRepository;

    public ProductInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Product p = new Product(UUID.randomUUID(), new BigDecimal("10"));
        Product p2 = new Product(UUID.randomUUID(), new BigDecimal("5"));
        Product p3 = new Product(UUID.randomUUID(), new BigDecimal("4"));
        Product p4 = new Product(UUID.randomUUID(), new BigDecimal("16.33"));
        List.of(p, p2, p3, p4).forEach(product -> {
            productRepository.save(product);
            logger.info("Added product " + product);
        });

    }

}
