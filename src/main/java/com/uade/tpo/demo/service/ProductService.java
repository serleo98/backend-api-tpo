package com.uade.tpo.demo.service;

import java.util.ArrayList;
import com.uade.tpo.demo.repository.cloudinary.ProductRepository;


public class ProductService {

    
    public ArrayList<Product> getProducts() {
        ProductRepository productRepository = new ProductRepository();
        return productRepository.getProducts();
    }
    

    public String createProducto(String entity) {         
        ProductRepository productRepository = new ProductRepository();
        return productRepository.createProducto(entity);
    }
    
}
