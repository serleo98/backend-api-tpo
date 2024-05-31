package com.uade.tpo.demo.entity.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
@Getter
public class TransactionDTO {
    private Long id;
    private Date date;
    private Integer buyerId;
    private Integer sellerId;
}
