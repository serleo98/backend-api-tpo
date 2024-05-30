package com.uade.tpo.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.service.IProductService;
import com.uade.tpo.demo.service.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.net.URI;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired //Injector
    private IProductService productService;

    @GetMapping
    public ResponseEntity<ArrayList<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest ProductRequest) throws Exception {
        Product result = productService.createProduct(ProductRequest.getId(), ProductRequest.getName(),
                ProductRequest.getPrice(), ProductRequest.getDescription());
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);

    }
}
