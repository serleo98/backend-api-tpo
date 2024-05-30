package com.uade.tpo.demo.service;

import java.util.ArrayList;

public interface IProductService {
    public ArrayList<Product> getProducts();
    
    public Product createProduct(int newProductId, String newProductName, int newProductPrice, String newProductDescription) throws Exception;
} 
