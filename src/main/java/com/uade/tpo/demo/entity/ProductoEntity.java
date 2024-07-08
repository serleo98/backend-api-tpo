package com.uade.tpo.demo.entity;

import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @OneToMany
    @JoinColumn(referencedColumnName = "id") //TODO: Como se relaciona producto con esto
    private List<StockAndType> stock = new ArrayList<>();

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<ImageEntity> image;

    public void addAllStockFromDTO(List<StockAndTypeDto> stockAndType){
        if(this.stock == null){
            this.stock = new ArrayList<>();
        }
        for (StockAndTypeDto stock : stockAndType) {
            this.stock.add(fromDto(stock));
        }
    }

    public void addStockFromDTO(StockAndTypeDto stockAndType){
        if(this.stock == null){
            this.stock = new ArrayList<>();
        }
        this.stock.add(fromDto(stockAndType));
    }


    public void addStock(StockAndType stockAndType){
        if(this.stock == null){
            this.stock = new ArrayList<>();
        }
        this.stock.add(stockAndType);
    }


    public static StockAndType fromDto(StockAndTypeDto dto) {
        StockAndType stockAndType = new StockAndType();
        stockAndType.setQuantity(dto.getQuantity());
        stockAndType.setType(dto.getType());
        stockAndType.setColor(dto.getColor());
        stockAndType.setSex(dto.getSex());
        return stockAndType;
    }
}
