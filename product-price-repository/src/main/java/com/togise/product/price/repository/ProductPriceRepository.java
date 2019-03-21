package com.togise.product.price.repository;

public interface ProductPriceRepository {
    ProductPrice getProductPrice(int id);
    int putProductPrice(ProductPrice productPrice);
}
