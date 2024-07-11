package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;

public interface IProductService {
    Page<ProductoEntity> getProducts(PageRequest pageable);

    List<String> getBrands();

    Optional<ProductoEntity> getProductById(Integer productId);
    
    ProductoEntity createProduct(ProductDTO productDTO)
            throws Exception;

    ProductoEntity modifiProduct(ProductToModifiDTO productToModifiDTO)
            throws Exception;
    
    void purchaseProducts(List<Integer> ids, List<StockAndType> stocks, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount);

    List<ProductoEntity> getProductsBySellerId(Integer userId);

    List<ProductoEntity> getProductsWithStock();

    List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice, BigDecimal maxPrice);

} 
