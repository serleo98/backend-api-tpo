package com.uade.tpo.demo.repository.db;

import com.uade.tpo.demo.entity.StockAndType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockAndTypeRepository extends JpaRepository<StockAndType, UUID> {
}
