package com.togise.product.price.repository;

import java.util.Objects;

// TODO rename to product price
public class ProductPrice {
    private final Price currentPrice;
    private final int id;

    public ProductPrice(ProductPrice productPrice) {
        this(productPrice.currentPrice, productPrice.id);
    }

    public ProductPrice(Price currentPrice, int id) {
        this.currentPrice = currentPrice;
        this.id = id;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice)) return false;
        ProductPrice productPrice = (ProductPrice) o;
        return id == productPrice.id &&
                Objects.equals(currentPrice, productPrice.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPrice, id);
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "currentPrice=" + currentPrice +
                ", id=" + id +
                '}';
    }
}
