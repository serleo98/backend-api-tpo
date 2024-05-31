package com.uade.tpo.demo.repository.db;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.User;


@Repository
public interface IProductRepository extends JpaRepository<ProductoEntity, Integer> {
    
    @Query(value = "SELECT p FROM ProductoEntity p WHERE p.name = ?1")
    List<ProductoEntity> findByName(String name);

    List<ProductoEntity> findByPublisherId(User user);
    List<ProductoEntity> findByBrand(String brand);
    List<ProductoEntity> findByCategory(String category);
    List<ProductoEntity> findByDescription(String description);
    List<ProductoEntity> findByPrice(BigDecimal price);

    Collection<? extends ProductoEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    Collection<? extends ProductoEntity> findByPriceGreaterThanEqual(BigDecimal minPrice);

    Collection<? extends ProductoEntity> findByPriceLessThanEqual(BigDecimal maxPrice);

    //@Query(value = "")
    //Integer findStock(ProductoEntity product);
       
} 

