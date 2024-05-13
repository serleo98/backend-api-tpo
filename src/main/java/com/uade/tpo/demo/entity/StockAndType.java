package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table
public class StockAndType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @UuidGenerator
    private UUID id;
    private String description;
    private String name;
    private String type;
    private String color;
    private Integer quantity;
}
