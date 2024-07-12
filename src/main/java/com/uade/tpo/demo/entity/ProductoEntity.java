package com.uade.tpo.demo.entity;

import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoEntity implements Serializable {

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
    private BigDecimal size;
    @Column 
    private String color;
    @Column
    private String sex;
    @Column
    private Integer stock;
    @Column
    private String image;
    private Status status;


}
