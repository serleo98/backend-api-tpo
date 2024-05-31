package com.uade.tpo.demo.service.transactionDetailsService.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.entity.dto.TransactionDetailsDTO;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.service.exceptions.TransactionNotFoundException;
import com.uade.tpo.demo.service.transactionDetailsService.TransactionDetailsService;

public class TransactionsDetailsServiceImpl implements TransactionDetailsService {

    @Autowired
    private TransactionDetailsRepository transactionsDetailsRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    public TransactionDetailsDTO getDetailById(Long id) {
        Optional<TransactionDetailsEntity> transactionDetail = transactionsDetailsRepository.findById(id);
        
        if(!transactionDetail.isEmpty()){
            TransactionDetailsDTO detailDTO = TransactionDetailsDTO.builder()
                .id(transactionDetail.get().getId())
                .transactionId(transactionDetail.get().getTransaction().getId())
                .productId(transactionDetail.get().getProduct().getId())
                .unitPrice(transactionDetail.get().getUnitPrice())
                .quantity(transactionDetail.get().getQuantity())
                .build();
                return detailDTO;
        }
        else{
            throw new TransactionNotFoundException("Transaction detail with id" + id + "not found");
        }
    }

    public List<TransactionDetailsDTO> getDetailsByTransactionId(Long id) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);
        if(!transactionEntity.isEmpty()){
            List<TransactionDetailsDTO> transactionDetailsDTO = new ArrayList<>();
            List<TransactionDetailsEntity> transactionDetails = transactionsDetailsRepository.findByTransaction(transactionEntity.get());
            for(TransactionDetailsEntity detail : transactionDetails){
                TransactionDetailsDTO detailDTO = TransactionDetailsDTO.builder()
                .id(detail.getId())
                .transactionId(id)
                .productId(detail.getProduct().getId())
                .unitPrice(detail.getUnitPrice())
                .quantity(detail.getQuantity())
                .build();

                transactionDetailsDTO.add(detailDTO);
            }
            return transactionDetailsDTO;
        }
        else{
            throw new TransactionNotFoundException("Transaction with id: " + id + "not found");
        }
    }

    public List<TransactionDetailsDTO> getDetailsByProductId(int id) {
        Optional<ProductoEntity> productEntity = productRepository.findById(id);
        if(!productEntity.isEmpty()){
            List<TransactionDetailsDTO> transactionDetailsDTO = new ArrayList<>();
            List<TransactionDetailsEntity> transactionDetails = transactionsDetailsRepository.findByProduct(productEntity.get());
            for(TransactionDetailsEntity detail : transactionDetails){
                TransactionDetailsDTO detailDTO = TransactionDetailsDTO.builder()
                .id(detail.getId())
                .transactionId(detail.getTransaction().getId())
                .productId(id)
                .unitPrice(detail.getUnitPrice())
                .quantity(detail.getQuantity())
                .build();

                transactionDetailsDTO.add(detailDTO);
            }
            return transactionDetailsDTO;
        }
        else{
            throw new TransactionNotFoundException("Product with id: " + id + "not found");
        }    
    }
    
}
