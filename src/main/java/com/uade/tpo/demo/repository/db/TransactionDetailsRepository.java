package com.uade.tpo.demo.repository.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetailsEntity, Long>{
    List<TransactionDetailsEntity> findByProduct(ProductoEntity product);
} 
