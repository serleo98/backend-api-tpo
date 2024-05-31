package com.uade.tpo.demo.service.exceptions;
public class ProductNotFoundExcepetion extends RuntimeException {
    public ProductNotFoundExcepetion(String message) {
        super(message);
    }
}