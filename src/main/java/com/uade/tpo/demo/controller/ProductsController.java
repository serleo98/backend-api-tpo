package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.service.productService.IProductService;
import com.uade.tpo.demo.utils.AuthUtils;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductoEntity> createProduct(@RequestBody ProductoEntity productRequest) throws Exception {
    ProductoEntity result = productService.createProduct(productRequest.getPublisherId(), productRequest.getBrand(), productRequest.getCategory(), productRequest.getName(), productRequest.getPrice(), productRequest.getDescription(), productRequest.getStock(), productRequest.getImage());
    return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
}

    @PutMapping("/edit/{id}")
    public ProductoEntity updateProduct(@PathVariable Integer id, @RequestBody ProductoEntity product) {
        product.setId(id);
        return productService.updateProduct(product);
    }
    
}