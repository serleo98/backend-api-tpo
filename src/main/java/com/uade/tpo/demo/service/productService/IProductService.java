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
    public Page<ProductoEntity> getProducts(PageRequest pageable);

    public Optional<ProductoEntity> getProductById(Integer productId);
    
    public ProductoEntity createProduct(ProductDTO productDTO)
            throws Exception;

    public ProductoEntity modifiProduct(ProductToModifiDTO productToModifiDTO)
            throws Exception;
    
    public void purchaseProducts(List<Integer> ids, List<StockAndType> stocks, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount);

    public List<ProductoEntity> getProductsBySellerId(Integer userId);

    public List<ProductoEntity> getProductsWithStock();

    public List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice, BigDecimal maxPrice);
        

} 
