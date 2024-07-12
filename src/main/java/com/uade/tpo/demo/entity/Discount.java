package com.uade.tpo.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal value;
    private DiscountStatus status;
    private Integer amountOfUse;
    private LocalDateTime expiredAt;

}
