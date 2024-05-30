package com.uade.tpo.demo.service.transactionService.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.CartEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.exceptions.TransactionNotFoundException;
import com.uade.tpo.demo.service.transactionService.TransactionService;
import lombok.Builder;
import lombok.Data;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    public TransactionDTO getTransactionById(Long id) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);
        
        if(!transactionEntity.isEmpty()){
            TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(transactionEntity.get().getId())
                .date(transactionEntity.get().getDate())
                .cartId(transactionEntity.get().getCartId())
                .buyerId(transactionEntity.get().getBuyerId())
                .sellerId(transactionEntity.get().getSellerId())
                .build();
                return transactionDTO;
        }
        else{
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByBuyerId(int id) {
        List<TransactionEntity> transactionEntity = transactionRepository.findByBuyer(id);
        List<TransactionDTO> transactions = new ArrayList<>();
        for(TransactionEntity t : transactionEntity){
            TransactionDTO transactionDTO = TransactionDTO.builder()
            .id(t.getId())
            .date(t.getDate())
            .cartId(t.getCartId())
            .buyerId(t.getBuyerId())
            .sellerId(t.getSellerId())
            .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public List<TransactionDTO> getTransactionsBySellerId(int id) {
        List<TransactionEntity> transactionEntity = transactionRepository.findBySeller(id);
        List<TransactionDTO> transactions = new ArrayList<>();
        for(TransactionEntity t : transactionEntity){
            TransactionDTO transactionDTO = TransactionDTO.builder()
            .id(t.getId())
            .date(t.getDate())
            .cartId(t.getCartId())
            .buyerId(t.getBuyerId())
            .sellerId(t.getSellerId())
            .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public void createTransaction(Date date, CartEntity cart, User buyer, User seller) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
            .date(date)
            .cart(cart)
            .buyer(buyer)
            .seller(seller)
            .build();

            transactionRepository.save(transactionEntity);
    }
    
}
