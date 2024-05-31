package com.uade.tpo.demo.entity.dto;

import com.uade.tpo.demo.entity.SexEnum;
import com.uade.tpo.demo.entity.StockAndType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import static jakarta.persistence.GenerationType.AUTO;

@Data
public class StockAndTypeDto {

    //Donde y cuando se hace el guardado de datos de StockAndType (Y si tengo que armar el constructor)
    //@UuidGenerato
    //private String description;
    //private String name;
    private String type; //talle
    private String color;

    private SexEnum sex;
    private Integer quantity;

    public static StockAndTypeDto fromEntity(StockAndType stockAndType) {
        StockAndTypeDto dto = new StockAndTypeDto();
        dto.setQuantity(stockAndType.getQuantity());
        dto.setSex(stockAndType.getSex());
        dto.setType(stockAndType.getType());
        dto.setColor(stockAndType.getColor());
        return dto;
    }
}
