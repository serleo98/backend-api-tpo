package com.uade.tpo.demo.service.discountService;

import com.uade.tpo.demo.entity.Discount;
import com.uade.tpo.demo.repository.db.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Discount createDiscount(BigDecimal value, Integer amountOfUse, LocalDateTime expiredAt) {

        Discount newDiscount = Discount.builder()
                .value(value)
                .amountOfUse(amountOfUse)
                .expiredAt(expiredAt)
                .build();
        discountRepository.saveAndFlush(newDiscount);

        return newDiscount;
    }
}
