package com.uade.tpo.demo.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uade.tpo.demo.entity.TransactionDetailsEntity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
@Getter
public class TransactionDTO {
    private Long id;
    private Date date;
    private String buyerId;
    private String sellerId;
    private float saleValue;
    private float discount;
    private float totalValue;
    private List<TransactionDetailsEntity> details = new ArrayList<>();
}
