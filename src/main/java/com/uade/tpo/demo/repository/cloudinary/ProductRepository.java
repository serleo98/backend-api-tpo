package com.uade.tpo.demo.repository.cloudinary;

import java.util.ArrayList;
import java.util.Arrays;
import com.uade.tpo.demo.service.Product;
import lombok.Data;



@Data
public class ProductRepository { //TODO: Conectar con el motor de base de datos
    
    private ArrayList<Product> products;

    public ProductRepository() {
        products = new ArrayList<Product>(
                Arrays.asList(
                    Product.builder().id(1).name("Zapatilla1").price(100).description("Description 1").build(),
                    Product.builder().id(2).name("Zapatilla2").price(200).description("Description 2").build(),
                    Product.builder().id(3).name("Zapatilla3").price(300).description("Description 3").build()
                )
        );
            }
        
    

    public ArrayList<Product> getProducts() {
        return this.products;
    }
            
        
    public Product createProduct(int newProductId, String newProductName, int newProductPrice, String newProductDescription) {
        Product newProduct = Product.builder()
                .id(newProductId)
                .name(newProductName)
                .price(newProductPrice)
                .description(newProductDescription)
                .build();
        this.products.add(newProduct);
        return newProduct;
    }
}
