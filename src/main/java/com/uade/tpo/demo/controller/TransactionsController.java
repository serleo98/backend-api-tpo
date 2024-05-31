package com.uade.tpo.demo.controller;

import java.util.Date;
import java.util.List;

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
    TransactionRepository transactionRepository;

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    TransactionDetailsService transactionDetailsService;
    
    @GetMapping("/detail{id}")
    public TransactionDetailsDTO getDetailById(Long id){
        return transactionDetailsService.getDetailById(id);
    }

    @GetMapping("/producttransactions{id}")
    public  List<TransactionDetailsDTO> getDetailsByProductId(int id){
        return transactionDetailsService.getDetailsByProductId(id);
    }

    @GetMapping("/transactiondetails-{id}")
    public List<TransactionDetailsDTO> getDetailsByTransactionId(Long id){
        return transactionDetailsService.getDetailsByTransactionId(id);
    }

    @GetMapping("/transaction-{id}")
    public TransactionDTO getTransactionById(Long id){
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/transactions-user{id}")
    public List<TransactionDTO> getAllTransactionsByUser(Integer id){
        List<TransactionDTO> transactions = transactionService.getTransactionsByBuyerId(id);
        transactions.addAll(transactionService.getTransactionsBySellerId(id));
        return transactions;
    }

    @GetMapping("/usersales{id}")
    public List<TransactionDTO> getSalesByUserId(Integer id){
        List<TransactionDTO> sales = transactionService.getTransactionsBySellerId(id);
        return sales;
    } 

    @GetMapping("/userpurchases{id}")
    public List<TransactionDTO> getPurchasesByUserId(Integer id){
        List<TransactionDTO> purchases = transactionService.getTransactionsByBuyerId(id);
        return purchases;
    } 

    @PostMapping("/createTransaction")
    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount){
        transactionService.createTransaction(date, productsId, quantities, buyerId, sellerId, discount);
    }

    @GetMapping("/transactions")
    public List<TransactionEntity> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping("/details")
    public List<TransactionDetailsEntity> getAllDetails(){
        return transactionDetailsRepository.findAll();
    }


}