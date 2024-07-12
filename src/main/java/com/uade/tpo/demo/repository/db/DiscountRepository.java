package com.uade.tpo.demo.repository.db;

import com.uade.tpo.demo.entity.Discount;
import com.uade.tpo.demo.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {
}
