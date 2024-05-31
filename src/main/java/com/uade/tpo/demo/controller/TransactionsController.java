package com.uade.tpo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.demo.entity.dto.TransactionDetailsDTO;
import com.uade.tpo.demo.service.transactionDetailsService.TransactionDetailsService;
import com.uade.tpo.demo.service.transactionService.TransactionService;

public class TransactionsController{

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    TransactionDetailsService transactionDetailsService;
    
    public TransactionDetailsDTO getDetailById(Long id){
        return transactionDetailsService.getDetailById(id);
    }

}