package com.uade.tpo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.dto.InformProblemDTO;
import com.uade.tpo.demo.service.InformProblemService.InformProblemInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/informar problema")
public class InformProblemController {
    private final InformProblemInterface InformProblemService;

    @Autowired
    public InformProblemController(InformProblemInterface informContactService){
        this.InformProblemService = informContactService;
    }

    @PostMapping("/")
    public ResponseEntity<String> guardarInforme(@RequestBody InformProblemDTO informProblemDTO) {
        try{
            InformProblemService.guardarContacto(informProblemDTO);
            return ResponseEntity.ok("Informe registrado exitosamente");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        
        
    }
    
}
