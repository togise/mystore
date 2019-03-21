package com.togise.mystore.web.controller;

import com.togise.mystore.web.view.ProductView;
import com.togise.product.price.repository.ProductPrice;
import com.togise.product.price.repository.ProductPriceRepository;
import com.togise.redsky.client.NamingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private NamingClient redskyClient;

    @GetMapping(
            produces = "application/json",
            value = "{id}"
    )
    public ProductView getProduct(@PathVariable int id) {
        String name = redskyClient.getProductName(id);
        ProductPrice productPrice = productPriceRepository.getProductPrice(id);
        return new ProductView(productPrice, name);
    }
}