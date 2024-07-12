package com.uade.tpo.demo.controller;

import java.util.Date;
import java.util.List;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.entity.dto.TransactionDetailsDTO;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.service.transactionDetailsService.TransactionDetailsService;
import com.uade.tpo.demo.service.transactionService.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/transactions")
public class TransactionsController{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private TransactionDetailsService transactionDetailsService;
    
    @GetMapping("/detail{id}")
    @SecurityRequirement(name = "bearer")
    public TransactionDetailsDTO getDetailById(Long id){
        return transactionDetailsService.getDetailById(id);
    }

    @GetMapping("/product/{id}")
    @SecurityRequirement(name = "bearer")
    public  List<TransactionDetailsDTO> getDetailsByProductId(int id){
        return transactionDetailsService.getDetailsByProductId(id);
    }

    @GetMapping("/details/{id}")
    @SecurityRequirement(name = "bearer")
    public List<TransactionDetailsDTO> getDetailsByTransactionId(Long id){
        return transactionDetailsService.getDetailsByTransactionId(id);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    public TransactionDTO getTransactionById(Long id){
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/user/")
    @SecurityRequirement(name = "bearer")
    public List<TransactionDTO> getAllTransactionsByUser(){
        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        List<TransactionDTO> transactions = transactionService.getTransactionsByBuyerId(currentUser.getId());
        transactions.addAll(transactionService.getTransactionsBySellerId(currentUser.getId()));
        return transactions;
    }

    @GetMapping("/user/sales/")
    @SecurityRequirement(name = "bearer")
    public List<TransactionDTO> getSalesByUserId(){
        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        List<TransactionDTO> sales = transactionService.getTransactionsBySellerId(currentUser.getId());
        return sales;
    } 

    @GetMapping("/user/purchases/")
    @SecurityRequirement(name = "bearer")
    public List<TransactionDTO> getPurchasesByUserId(){
        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        List<TransactionDTO> purchases = transactionService.getTransactionsByBuyerId(currentUser.getId());
        return purchases;
    } 

    @Deprecated
    @PostMapping("/create/transaction")
    @SecurityRequirement(name = "bearer")
    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, float discount){
        transactionService.createTransaction(date, productsId, quantities, buyerId, discount);
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearer")
    public List<TransactionEntity> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping("/details")
    @SecurityRequirement(name = "bearer")
    public List<TransactionDetailsEntity> getAllDetails(){
        return transactionDetailsRepository.findAll();
    }

}