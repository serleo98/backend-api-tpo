package com.uade.tpo.demo.service.exceptions;
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}