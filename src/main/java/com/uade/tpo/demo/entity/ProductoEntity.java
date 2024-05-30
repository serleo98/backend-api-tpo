package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table
public class ProductoEntity {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User publisherId;
    private String brand;
    private String category;
    private String name;
    private BigDecimal price;
    private String description;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<StockAndType> stock;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<ImageEntity> image;
}
