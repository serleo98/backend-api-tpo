package com.uade.tpo.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.entity.dto.TransactionDetailsDTO;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.service.transactionDetailsService.TransactionDetailsService;
import com.uade.tpo.demo.service.transactionService.TransactionService;

public class TransactionsController{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    TransactionDetailsService transactionDetailsService;
    
    public TransactionDetailsDTO getDetailById(Long id){
        return transactionDetailsService.getDetailById(id);
    }

    public  List<TransactionDetailsDTO> getDetailsByProductId(int id){
        return transactionDetailsService.getDetailsByProductId(id);
    }

    public List<TransactionDetailsDTO> getDetailsByTransactionId(Long id){
        return transactionDetailsService.getDetailsByTransactionId(id);
    }

    public TransactionDTO getTransactionById(Long id){
        return transactionService.getTransactionById(id);
    }

    public List<TransactionDTO> getAllTransactionsByUser(Integer id){
        List<TransactionDTO> transactions = transactionService.getTransactionsByBuyerId(id);
        transactions.addAll(transactionService.getTransactionsBySellerId(id));
        return transactions;
    }

    public List<TransactionDTO> getSalesByUserId(Integer id){
        List<TransactionDTO> sales = transactionService.getTransactionsBySellerId(id);
        return sales;
    } 

    public List<TransactionDTO> getPurchasesByUserId(Integer id){
        List<TransactionDTO> purchases = transactionService.getTransactionsByBuyerId(id);
        return purchases;
    } 

    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount){
        transactionService.createTransaction(date, productsId, quantities, buyerId, sellerId, discount);
    }

    public List<TransactionEntity> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public List<TransactionDetailsEntity> getAllDetails(){
        return transactionDetailsRepository.findAll();
    }


}