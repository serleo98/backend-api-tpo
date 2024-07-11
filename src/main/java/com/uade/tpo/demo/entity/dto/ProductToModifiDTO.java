package com.uade.tpo.demo.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductToModifiDTO {

    private Integer id;
    private String brand;
    private String category;
    private String name;
    private BigDecimal price;
    private BigDecimal size;
    private String color;
    private String sex;
    private Integer stock;
    private String image;
}
