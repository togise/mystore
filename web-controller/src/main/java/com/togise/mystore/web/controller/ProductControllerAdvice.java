package com.togise.mystore.web.controller;

import com.togise.http.client.SimpleHttpClient.SimpleHttpGetRequest.NotFoundException;
import com.togise.product.repository.dynamodb.ProductDynamoDBRepo.ItemNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ProductControllerAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({ItemNotFoundException.class, NotFoundException.class})
    public void handleNotFoundException(){}
}
