package com.uade.tpo.demo.entity.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
@Getter
public class TransactionDetailsDTO {
    private Long id;
    private Long transactionId;
    private Integer productId;
    private BigDecimal unitPrice;
    private Integer quantity;
}