package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.entity.User;

public interface IProductService {
    public Page<ProductoEntity> getProducts(PageRequest pageable);

    public Optional<ProductoEntity> getProductById(Integer productId);
    
    public ProductoEntity createProduct(User publisherId, String brand, String category, String name,
            BigDecimal price, String description, List<StockAndType> stock, List<ImageEntity> image)
            throws Exception;

} 
