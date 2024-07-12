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
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    Page<ProductoEntity> getProducts(PageRequest pageable);

    void inactivateProduct(Integer productId);

    List<String> getBrands();

    Optional<ProductoEntity> getProductById(Integer productId);
    
    ProductoEntity createProduct(ProductDTO productDTO)
            throws Exception;

//     void addPhoto(MultipartFile img, Integer productId) throws Exception;

    ProductoEntity modifyProduct(ProductToModifiDTO productToModifiDTO)
            throws Exception;
    
    void purchaseProducts(List<Integer> ids, List<Integer> quantities, Integer buyerId, float discount);

    List<ProductoEntity> getProductsBySellerId(Integer userId);

    List<ProductoEntity> getProductsWithStock();

    List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice, BigDecimal maxPrice);

} 
