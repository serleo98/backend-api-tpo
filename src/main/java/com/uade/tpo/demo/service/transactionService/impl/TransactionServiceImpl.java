package com.uade.tpo.demo.service.transactionService.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.ProductoEntity;
=======
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.CartEntity;
>>>>>>> 7eb9df4 (entidad, logica y repos)
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
<<<<<<< HEAD
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
=======
    @Autowired
    private TransactionRepository transactionRepository;

>>>>>>> 7eb9df4 (entidad, logica y repos)

    public TransactionDTO getTransactionById(Long id) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);
        
        if(!transactionEntity.isEmpty()){
            TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(transactionEntity.get().getId())
                .date(transactionEntity.get().getDate())
<<<<<<< HEAD
=======
                .cartId(transactionEntity.get().getCartId())
>>>>>>> 7eb9df4 (entidad, logica y repos)
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
<<<<<<< HEAD
=======
            .cartId(t.getCartId())
>>>>>>> 7eb9df4 (entidad, logica y repos)
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
<<<<<<< HEAD
=======
            .cartId(t.getCartId())
>>>>>>> 7eb9df4 (entidad, logica y repos)
            .buyerId(t.getBuyerId())
            .sellerId(t.getSellerId())
            .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
<<<<<<< HEAD
    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, int buyerId, int sellerId) {
        
        // Verificar que las listas de productos y cantidades tengan el mismo tamaño
        if (productsId.size() != quantities.size()) {
            throw new IllegalArgumentException("La lista de productos y cantidades debe tener el mismo tamaño");
        }
        
        //busco el comprador y el vendedor
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new RuntimeException("Comprador no encontrado"));
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
        
        //armo la transaccion, para obtener su id y luego armar los details con este
        TransactionEntity transactionEntity = TransactionEntity.builder()
            .date(date)
=======
    public void createTransaction(Date date, CartEntity cart, User buyer, User seller) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
            .date(date)
            .cart(cart)
>>>>>>> 7eb9df4 (entidad, logica y repos)
            .buyer(buyer)
            .seller(seller)
            .build();

<<<<<<< HEAD
        transactionRepository.save(transactionEntity);


        //Armo todas las entradas en TransactionDetails
        for(int i = 0; i < productsId.size(); i++){
            int productId = productsId.get(i);
            int quantity = quantities.get(i);
            ProductoEntity productEntity = productRepository.findById(p);
            TransactionDetailsEntity transactionDetail = transactionDetail.builder()
                .transaction(transactionEntity)
                .product(productEntity)
                .unitPrice(productEntity.getPrice())
                .quantity(quantity)
                .build();

            transactionDetailsRepository.save(transactionDetail);
        }



            
=======
            transactionRepository.save(transactionEntity);
>>>>>>> 7eb9df4 (entidad, logica y repos)
    }
    
}
