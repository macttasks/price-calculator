package com.mt.calculator.domain;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> products;

    public InMemoryProductRepository() {
        products = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public void save(Product product) {
        products.put(product.id(), product);
    }
}
