package com.uade.tpo.demo.entity.dto;

import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {


    private Integer publisherId;
    private String brand;
    private String category;
    private String name;
    private BigDecimal price;
    private String description;

    private List<StockAndTypeDto> stock;
}
