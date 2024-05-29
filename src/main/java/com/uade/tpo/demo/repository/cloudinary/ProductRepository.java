package com.uade.tpo.demo.repository.cloudinary;



import java.util.ArrayList;
import java.util.Arrays;

import com.uade.tpo.demo.service.Product;

import lombok.Data;

@Data
public class ProductRepository { //TODO: Conectar con el motor de base de datos
    public ArrayList<Product> products = new ArrayList<Product>(
        Arrays.asList(
        Product.builder().id(1).name("Product 1").price(100).description("Description 1").build(),
        Product.builder().id(2).name("Product 2").price(200).description("Description 2").build(),
        Product.builder().id(3).name("Product 3").price(300).description("Description 3").build()
            ));
    

    public ArrayList<Product> getProducts() {
        return this.products;
    }
            
        
    public String createProducto(String entity) {
        return null;
    }
}
