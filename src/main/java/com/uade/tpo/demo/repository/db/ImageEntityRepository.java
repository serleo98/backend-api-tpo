package com.uade.tpo.demo.repository.db;

import com.uade.tpo.demo.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
}
