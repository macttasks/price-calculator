package com.mt.calculator.domain;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Optional<Product> findById(UUID id);

    void save(Product product);
}
