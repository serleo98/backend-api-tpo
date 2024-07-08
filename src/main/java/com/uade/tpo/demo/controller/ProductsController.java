package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.repository.db.IStock;
import com.uade.tpo.demo.service.exceptions.StockNotFoundException;
import com.uade.tpo.demo.service.productService.IProductService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
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
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<ProductoEntity> getProductById(@PathVariable Integer productId) {
        Optional<ProductoEntity> result = productService.getProductById(productId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/createe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> createProduct(@RequestPart("Imagenes") List<MultipartFile> imagenes,
                                                @RequestParam String brand,
                                                @RequestParam String Category,
                                                @RequestParam String name,
                                                @RequestParam BigDecimal Precio,
                                                @RequestParam String Description
    ) throws Exception {
        ProductDTO producto = ProductDTO.builder()
                .brand(brand)
                .category(Category)
                .name(name)
                .price(Precio)
                .description(Description)
                .build();
        
        try {
            
            productService.createProduct(producto, imagenes);
            return ResponseEntity.ok("productos registrado exitosamente");
        } catch (Exception e) {
        
            return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping(value = "/addStock/{productId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> createProduct(@RequestParam Integer productId ,@RequestBody List<StockAndTypeDto> stockAndTypeDto) throws Exception {
        try {
            productService.addStock(productId,stockAndTypeDto);
            return ResponseEntity.ok("Stock agregado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    
    @PutMapping(value = "/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<ProductoEntity> modifiProduct(@RequestBody ProductToModifiDTO productToModifiDTO,@RequestPart("Imagenes") List<MultipartFile> imagenes) throws Exception {

        ProductoEntity result = productService.modifiProduct(productToModifiDTO,imagenes);

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

    @PostMapping("/purchase")
    public void purchaseProducts(List<Integer> productIds, List<Integer> stockIds, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount) {
        List<StockAndType> stocks = stockRepository.findAllById(stockIds);
        if (!stocks.isEmpty()) {
            productService.purchaseProducts(productIds, stocks, quantities, buyerId, sellerId, discount);
        } else {
            throw new StockNotFoundException("Stocks not found");
        }
    }

}
