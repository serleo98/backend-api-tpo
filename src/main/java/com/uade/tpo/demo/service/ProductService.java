package com.uade.tpo.demo.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.uade.tpo.demo.repository.cloudinary.ProductRepository;


@Service
public class ProductService implements IProductService{
    private ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    public ArrayList<Product> getProducts() {
        return productRepository.getProducts();
    }
    

    public Product createProduct(int newProductId, String newProductName, int newProductPrice, String newProductDescription) throws Exception {         
        ArrayList<Product> products = productRepository.getProducts();
        if(products.stream().anyMatch(
            product -> product.getId() == newProductId || product.getName().equals(newProductName))) 
            throw new Exception("Ya existe el producto con id: " + newProductId + " o nombre: " + newProductName + " en la base de datos.");
        return productRepository.createProduct(newProductId, newProductName, newProductPrice, newProductDescription);
        }
    }
    

