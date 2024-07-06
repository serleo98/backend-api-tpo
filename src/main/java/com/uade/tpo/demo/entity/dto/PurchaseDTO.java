package com.uade.tpo.demo.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDTO {

    private List<PurchaseProductDTO> products;
    private String discountCode;

    @Data
    public static class PurchaseProductDTO {
        private Integer productId;
        private Integer stockId;
        private Integer quantity;
    }
}
