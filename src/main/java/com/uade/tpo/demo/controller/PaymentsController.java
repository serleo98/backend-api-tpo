package com.uade.tpo.demo.controller;


import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.PurchaseDTO;
import com.uade.tpo.demo.service.purchaseService.PurchaseService;
import com.uade.tpo.demo.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/product")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity purchase(@RequestBody PurchaseDTO purchaseDTO){

        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        purchaseService.purchase(purchaseDTO, currentUser);
        return ResponseEntity.ok("Purchase completed");
    }

}
