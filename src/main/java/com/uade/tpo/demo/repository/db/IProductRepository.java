package com.uade.tpo.demo.repository.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.demo.entity.ProductoEntity;


@Repository
public interface IProductRepository extends JpaRepository<ProductoEntity, Integer> {
    
    @Query(value = "SELECT p FROM ProductoEntity p WHERE p.name = ?1")
    List<ProductoEntity> findByName(String name);

    //@Query(value = "")
    //Integer findStock(ProductoEntity product);
       
} 

