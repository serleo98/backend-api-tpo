package com.uade.tpo.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.service.IProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductoEntity>> getProducts(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
    if (page == null || size == null)
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
    return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
}

    @GetMapping("/{productId}")
    public ResponseEntity<ProductoEntity> getProductById(@PathVariable Integer productId) {
        Optional<ProductoEntity> result = productService.getProductById(productId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/create")
    public ResponseEntity<ProductoEntity> createProduct(@RequestBody ProductoEntity productRequest) throws Exception{
    ProductoEntity result = productService.createProduct(productRequest.getPublisherId(), productRequest.getBrand(), productRequest.getCategory(), productRequest.getName(), productRequest.getPrice(), productRequest.getDescription(), productRequest.getStock(), productRequest.getImage());
    return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
}
    
}
