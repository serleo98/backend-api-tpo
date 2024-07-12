package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.service.discountService.DiscountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping()
    @SecurityRequirement(name = "bearer")
    public ResponseEntity login(@RequestParam BigDecimal value,@RequestParam Integer amountOfUse) {

        log.info("Creating discount with value: " + value + " amount of use: " + amountOfUse );
        return ResponseEntity.ok(discountService.createDiscount(value, amountOfUse));
    }
}
