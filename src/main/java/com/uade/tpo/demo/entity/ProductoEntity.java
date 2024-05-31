package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User publisherId;
    private String brand;
    @Column
    private String category;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private String description;
    //@Column
    //private Integer publisherId;
    

    @OneToMany
    @JoinColumn(referencedColumnName = "id") //TODO: Como se relaciona producto con esto
    private List<StockAndType> stock;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<ImageEntity> image;
}
