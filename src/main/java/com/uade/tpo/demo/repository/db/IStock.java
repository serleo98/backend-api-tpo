package com.uade.tpo.demo.repository.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.demo.entity.StockAndType;

@Repository
public interface IStock extends JpaRepository<StockAndType, Integer>{
    
}
