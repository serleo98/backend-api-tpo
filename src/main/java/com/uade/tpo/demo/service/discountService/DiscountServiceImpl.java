package com.uade.tpo.demo.service.discountService;

import com.uade.tpo.demo.entity.Discount;
import com.uade.tpo.demo.entity.Status;
import com.uade.tpo.demo.repository.db.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Discount createDiscount(BigDecimal value, Integer amountOfUse) {

        Discount newDiscount = Discount.builder()
                .value(value)
                .status(Status.ACTIVE)
                .amountOfUse(amountOfUse)
                .build();

        discountRepository.saveAndFlush(newDiscount);

        return newDiscount;
    }
}
