package com.togise.product.repository;

import java.util.Objects;

// TODO rename to product price
public class Product {
    private final Price currentPrice;
    private final int id;

    public Product(Product product) {
        this(product.currentPrice, product.id);
    }

    public Product(Price currentPrice, int id) {
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
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(currentPrice, product.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPrice, id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "currentPrice=" + currentPrice +
                ", id=" + id +
                '}';
    }
}
