package com.uade.tpo.demo.service.discountService;

import com.uade.tpo.demo.entity.Discount;
import com.uade.tpo.demo.entity.DiscountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface DiscountService {

    Discount createDiscount(BigDecimal value,Integer amountOfUse,LocalDateTime expiredAt);
}
