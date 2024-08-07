package com.uade.tpo.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @Column(name = "discount_value")
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private Status status ;
    private Integer amountOfUse;

    public boolean isAvailable(){
        return status.equals(Status.ACTIVE) && amountOfUse > 0;
    }

    public void inactivate(){
        status = Status.INACTIVE;
    }

}
