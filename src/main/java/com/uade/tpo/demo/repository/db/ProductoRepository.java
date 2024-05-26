package com.uade.tpo.demo.repository.db;

import com.uade.tpo.demo.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity,Integer> {
}
