package com.togise.product.price.repository;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    public enum Currency {
        USD
    }

    private final Currency currency;
    private final BigDecimal price;

    public Price(Currency currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price1 = (Price) o;
        return currency == price1.currency &&
                Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, price);
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency=" + currency +
                ", price=" + price +
                '}';
    }
}
