package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import com.uade.tpo.demo.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IStock stockRepository;


    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands() {
        return ResponseEntity.ok(productService.getBrands());
    }

    @GetMapping
    public ResponseEntity<Page<ProductoEntity>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/{productId}")
    //@SecurityRequirement(name = "bearer")
    public ResponseEntity<ProductoEntity> getProductById(@PathVariable Integer productId) {

        Optional<ProductoEntity> result = productService.getProductById(productId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();

    }


    @PostMapping("/create")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<ProductoEntity> createProduct(@RequestBody ProductDTO productRequest) throws Exception {

        ProductoEntity result = productService.createProduct(productRequest);

        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }

    // @PostMapping(value = "/addPhoto/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // @SecurityRequirement(name = "bearer")
    // public ResponseEntity<ProductoEntity> addPhoto(@RequestPart MultipartFile img, @RequestParam Integer productId) throws Exception {

    //      productService.addPhoto(img,productId);

    //     return ResponseEntity.ok().build();
    // }

    @PutMapping("/modify")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity modifyProduct(@RequestBody ProductToModifiDTO productToModifiDTO) throws Exception {

        productService.modifyProduct(productToModifiDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/stocked")
    public List<ProductoEntity> getStockedProducts() {
        return productService.getProductsWithStock();
    }

    @GetMapping("/by-seller")
    @SecurityRequirement(name = "bearer")
    public List<ProductoEntity> getProductsBySellerID() {
        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        return productService.getProductsBySellerId(currentUser.getId());
    }

    @GetMapping("/filtered")
    public List<ProductoEntity> getProductsFiltered(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        return productService.getProductsFiltered(brand, category, name, minPrice, maxPrice);
    }

    @PostMapping("/purchase")
    public void purchaseProducts(List<Integer> productIds, List<Integer> quantities, Integer buyerId, float discount) {
        productService.purchaseProducts(productIds, quantities, buyerId, discount);
    }

}
