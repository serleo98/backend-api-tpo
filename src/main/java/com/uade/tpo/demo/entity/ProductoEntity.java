package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table
public class ProductoEntity {

    public ProductoEntity() {
    }

    public ProductoEntity(Integer publisherId, String brand, String category, String name, BigDecimal price,
            String description, List<StockAndType> stock, List<ImageEntity> image) {
        if (publisherId == null || brand == null || category == null || name == null || price == null
                || description == null || stock == null || image == null)
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        this.publisherId = publisherId;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

<<<<<<< HEAD
    //@ManyToOne
    //@JoinColumn(referencedColumnName = "id")
    //@Column
    //private Integer publisherId;
    @Column
=======
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User publisherId;
>>>>>>> a7923022b0ec509c3e2fbb8b07e0f2e239176c4f
    private String brand;
    @Column
    private String category;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private String description;
    @Column
    private Integer publisherId;
    

    @OneToMany
    @JoinColumn(referencedColumnName = "id") //TODO: Como se relaciona producto con esto
    private List<StockAndType> stock;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<ImageEntity> image;
}
