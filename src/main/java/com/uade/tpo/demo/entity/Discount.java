package com.uade.tpo.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Discount implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal value;
    private DiscountStatus status = DiscountStatus.ACTIVE;
    private Integer amountOfUse;

    public boolean isAvailable(){
        return status.equals(DiscountStatus.ACTIVE) && amountOfUse > 0;
    }

    public void inactivate(){
        status = DiscountStatus.INACTIVE;
    }

}
