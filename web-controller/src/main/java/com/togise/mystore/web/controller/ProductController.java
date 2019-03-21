package com.togise.mystore.web.controller;

import com.togise.mystore.web.view.ProductView;
import com.togise.product.repository.Product;
import com.togise.product.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private NamingClient redskyClient;

    @GetMapping(
            produces = "application/json",
            value = "{id}"
    )
    public ProductView getProduct(@PathVariable int id) {
        String name = redskyClient.getProductName(id);
        Product product = productRepository.getProduct(id);
        return new ProductView(product, name);
    }
}