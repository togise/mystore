package com.togise.product.repository;

public interface ProductRepository {
    Product getProduct(int id);
    int putProduct(Product product);
}
