package com.uade.tpo.demo.repository.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.User;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findById(Long id);
    List<TransactionEntity> findByBuyer(User buyer);

    @Query("SELECT t FROM TransactionEntity t join ProductoEntity as p  where p.publisherId = ?1")
    List<TransactionEntity> findBySeller(Integer sellerId);
}
