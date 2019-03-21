package com.togise.mystore.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest
class ProductControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "Handle not found"() {
        expect:
        mvc.perform(get("/product/123"))
                .andExpect(status().isNotFound())
    }

    @Unroll
    def "Find Product with id #id" () {
        expect:
        mvc.perform(get("/product/${id}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        where:
        id << [13860428]
    }
}