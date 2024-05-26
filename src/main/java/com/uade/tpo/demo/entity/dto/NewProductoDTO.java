package com.uade.tpo.demo.entity.dto;

import com.uade.tpo.demo.entity.SexEnum;
import com.uade.tpo.demo.entity.StockAndType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class NewProductoDTO {

    private Integer publisherId;
    private String brand;
    private String category;
    private String name;
    private BigDecimal price;
    private String description;
    private List <StockAndType> stock;
    private SexEnum sex;
    private String imageUrl;
    private String imageDescription;
}
