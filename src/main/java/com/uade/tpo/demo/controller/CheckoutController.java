package com.uade.tpo.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.entity.ChekoutEntity;
import com.uade.tpo.demo.entity.dto.CheckoutDTO;
import com.uade.tpo.demo.repository.db.CheckOutRepository;
import com.uade.tpo.demo.service.checkoutService.CheckoutService;
import com.uade.tpo.demo.service.checkoutService.ICheckOutService;

@RestController
@RequestMapping("/CheckoutPago")
public class CheckoutController {
    @Autowired
    private CheckoutService CheckoutService;

    @PostMapping("/checkout")
    public ResponseEntity<String> guardarinforme(CheckoutDTO informProblemDTO) {

       
        try {
            
            CheckoutService.guardarinforme(informProblemDTO);
            return ResponseEntity.ok("Informe registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }   

}
