package com.uade.tpo.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.service.Product;
import com.uade.tpo.demo.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/products")
public class ProductsController {
    

    @GetMapping
    public ArrayList<Product> getProducts() {
        //TODO: process GET request
        ProductService productService = new ProductService();
        return productService.getProducts();
    }
    

    @PostMapping
    public String createProducto(@RequestBody String productId) {
        //TODO: process POST request            
        ProductService productService = new ProductService();
        return productService.createProducto(productId);
    }
    
    
    
}
