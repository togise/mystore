package com.togise.mystore.web.view;

import com.togise.product.repository.Product;

public class ProductView extends Product {

    private final String name;

    public ProductView(Product product, String name) {
        super(product);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
