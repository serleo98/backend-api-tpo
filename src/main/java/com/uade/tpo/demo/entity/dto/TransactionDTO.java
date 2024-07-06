package com.uade.tpo.demo.entity.dto;

import java.util.Date;
import java.util.List;

import com.uade.tpo.demo.entity.TransactionDetailsEntity;

import com.uade.tpo.demo.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
@Getter
public class TransactionDTO {
    private Long id;
    private Date date;
    private User buyerId;
    private float saleValue;
    private float discount;
    private float totalValue;
    private List<TransactionDetailsEntity> details;
}
