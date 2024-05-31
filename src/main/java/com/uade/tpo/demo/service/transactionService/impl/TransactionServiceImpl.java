package com.uade.tpo.demo.service.transactionService.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.exceptions.TransactionNotFoundException;
import com.uade.tpo.demo.service.exceptions.UserNotFoundException;
import com.uade.tpo.demo.service.transactionService.TransactionService;
import lombok.Builder;
import lombok.Data;

@Service
public class TransactionServiceImpl implements TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDTO getTransactionById(Long id) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);
        
        if(!transactionEntity.isEmpty()){
            TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(transactionEntity.get().getId())
                .date(transactionEntity.get().getDate())
                .buyerId(transactionEntity.get().getBuyerId())
                .sellerId(transactionEntity.get().getSellerId())
                .saleValue(transactionEntity.get().getSaleValue())
                .discount(transactionEntity.get().getDiscount())
                .totalValue(transactionEntity.get().getTotalValue())
                .build();
                return transactionDTO;
        }
        else{
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByBuyerId(Integer id) {
        Optional<User> buyer = userRepository.findById(id);
        if(buyer.isEmpty()){
            throw new UserNotFoundException("User with id" + id + "not found");
        }
        List<TransactionEntity> transactionEntity = transactionRepository.findByBuyer(buyer.get());
        List<TransactionDTO> transactions = new ArrayList<>();
        for(TransactionEntity t : transactionEntity){
            TransactionDTO transactionDTO = TransactionDTO.builder()
            .id(t.getId())
            .date(t.getDate())
            .buyerId(t.getBuyerId())
            .sellerId(t.getSellerId())
            .saleValue(t.getSaleValue())
            .discount(t.getDiscount())
            .totalValue(t.getTotalValue())
            .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public List<TransactionDTO> getTransactionsBySellerId(Integer id) {
        Optional<User> seller = userRepository.findById(id);
        if(seller.isEmpty()){
            throw new UserNotFoundException("User with id" + id + "not found");
        }
        List<TransactionEntity> transactionEntity = transactionRepository.findBySeller(seller.get());
        List<TransactionDTO> transactions = new ArrayList<>();
        for(TransactionEntity t : transactionEntity){
            TransactionDTO transactionDTO = TransactionDTO.builder()
            .id(t.getId())
            .date(t.getDate())
            .buyerId(t.getBuyerId())
            .sellerId(t.getSellerId())
            .saleValue(t.getSaleValue())
            .discount(t.getDiscount())
            .totalValue(t.getTotalValue())
            .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount) {
        
        // Verificar que las listas de productos y cantidades tengan el mismo tamaño
        if (productsId.size() != quantities.size()) {
            throw new IllegalArgumentException("La lista de productos y cantidades debe tener el mismo tamaño");
        }
        
        //busco el comprador y el vendedor
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("Comprador no encontrado"));
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException("Vendedor no encontrado"));
        
        //armo la transaccion, para obtener su id y luego armar los details con este
        TransactionEntity transactionEntity = TransactionEntity.builder()
            .date(date)
            .buyer(buyer)
            .seller(seller)
            .discount(discount)
            .build();


        //Armo todas las entradas en TransactionDetails
        float sum = 0;
        for(int i = 0; i < productsId.size(); i++){
            int productId = productsId.get(i);
            int quantity = quantities.get(i);
            ProductoEntity productEntity = productRepository.findById(productId);
            TransactionDetailsEntity transactionDetail = TransactionDetailsEntity.builder()
                .transaction(transactionEntity)
                .product(productEntity)
                .unitPrice(productEntity.getPrice())
                .quantity(quantity)
                .build();
            
            sum += (productEntity.getPrice().floatValue() * quantity);
            transactionDetailsRepository.save(transactionDetail);
        }

        transactionEntity.setSaleValue(sum);
        transactionEntity.setTotalValue(sum * discount / 100);
        transactionRepository.save(transactionEntity);



            
    }
    
}
