package com.togise.mystore.web.view;

import com.togise.product.price.repository.ProductPrice;

public class ProductView extends ProductPrice {

    private final String name;

    public ProductView(ProductPrice productPrice, String name) {
        super(productPrice);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
