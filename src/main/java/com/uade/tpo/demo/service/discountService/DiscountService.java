package com.uade.tpo.demo.service.discountService;

import com.uade.tpo.demo.entity.Discount;

import java.math.BigDecimal;

public interface DiscountService {

    Discount createDiscount(BigDecimal value,Integer amountOfUse);
}
