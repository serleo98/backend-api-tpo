package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table
public class ProductoEntity {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity publisherId;
    private String brand;
    private String category;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private BigDecimal price;
    private String description;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private StockAndType stock;

    private SexEnum sex;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ImageEntity image;
}
