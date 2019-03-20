package com.togise.product.repository;

import java.util.Objects;

public class Product {
    private final Price currentPrice;
    private final String name;
    private final int id;

    public Product(Price currentPrice, String name, int id) {
        this.currentPrice = currentPrice;
        this.name = name;
        this.id = id;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public String getName() {
        return name;
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
                Objects.equals(currentPrice, product.currentPrice) &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPrice, name, id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "currentPrice=" + currentPrice +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
