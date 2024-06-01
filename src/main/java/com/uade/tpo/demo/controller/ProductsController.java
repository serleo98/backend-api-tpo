package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import org.springframework.web.bind.annotation.*;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.repository.db.IStock;
import com.uade.tpo.demo.service.exceptions.StockNotFoundException;
import com.uade.tpo.demo.service.productService.IProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IStock stockRepository;


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
    @Transactional
    public ResponseEntity<ProductoEntity> createProduct(@RequestBody ProductDTO productRequest) throws Exception {

        ProductoEntity result = productService.createProduct(productRequest);

        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }

    @PutMapping("/modify")
    public ResponseEntity<ProductoEntity> modifiProduct(@RequestBody ProductToModifiDTO productToModifiDTO) throws Exception {

        ProductoEntity result = productService.modifiProduct(productToModifiDTO);

        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }

    @GetMapping("/stocked")
    public List<ProductoEntity> getStockedProducts() {
        return productService.getProductsWithStock();
    }

    @GetMapping("/seller{id}")
    public List<ProductoEntity> getProductsBySellerID(Integer id) {
        return productService.getProductsBySellerId(id);
    }

    @GetMapping("/filtered")
    public List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice, BigDecimal maxPrice) {
        //params in null if none
        return productService.getProductsFiltered(brand, category, name, minPrice, maxPrice);
    }

    @PostMapping("/buy")
    public void purchaseProducts(@RequestParam List<Integer> productIds, 
                             @RequestParam List<Integer> stockIds, 
                             @RequestParam List<Integer> quantities, 
                             @RequestParam Integer buyerId, 
                             @RequestParam Integer sellerId, 
                             @RequestParam float discount) {
        List<StockAndType> stocks = stockRepository.findAllById(stockIds);
        if (!stocks.isEmpty()) {
            productService.purchaseProducts(productIds, stocks, quantities, buyerId, sellerId, discount);
        } else {
            throw new StockNotFoundException("Stocks not found");
        }
    }

}
