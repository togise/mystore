package com.togise.mystore


import com.togise.mystore.web.controller.ProductController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired (required = false)
    private ProductController productController

    def "when context is loaded then all expected beans are created"() {
        expect: "the ProductController is created"
        productController
    }
}