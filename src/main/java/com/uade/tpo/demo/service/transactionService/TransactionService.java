package com.uade.tpo.demo.service.transactionService;

import java.util.Date;
import java.util.List;
<<<<<<< HEAD
=======

import com.uade.tpo.demo.entity.CartEntity;
import com.uade.tpo.demo.entity.User;
>>>>>>> 7eb9df4 (entidad, logica y repos)
import com.uade.tpo.demo.entity.dto.TransactionDTO;

public interface TransactionService {
    
    TransactionDTO getTransactionById(Long id);
    List<TransactionDTO> getTransactionsByBuyerId(int id);
    List<TransactionDTO> getTransactionsBySellerId(int id);
<<<<<<< HEAD
    void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, int buyerId, int sellerId);

=======
    void createTransaction(Date date, CartEntity cart, User buyer, User seller);
    
>>>>>>> 7eb9df4 (entidad, logica y repos)
}
