package com.uade.tpo.demo.entity;

import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.core.serializer.Deserializer;
//import org.hibernate.annotations.UuidGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table
public class StockAndType implements Serializable {

    //Donde y cuando se hace el guardado de datos de StockAndType (Y si tengo que armar el constructor)
    @Id
    @GeneratedValue(strategy = AUTO)
    //@UuidGenerator
    private Integer id;
    //private String description;
    //private String name;
    private String type; //talle
    private String color;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;
    private Integer quantity;

    public static StockAndType fromDto(StockAndTypeDto dto) {
        StockAndType stockAndType = new StockAndType();
        stockAndType.setQuantity(dto.getQuantity());
        stockAndType.setType(dto.getType());
        stockAndType.setColor(dto.getColor());
        stockAndType.setSex(dto.getSex());
        return stockAndType;
    }
}
