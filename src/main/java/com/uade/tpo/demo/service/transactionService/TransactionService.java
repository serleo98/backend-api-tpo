package com.uade.tpo.demo.service.transactionService;

import java.util.Date;
import java.util.List;
import com.uade.tpo.demo.entity.dto.TransactionDTO;

public interface TransactionService {
    
    TransactionDTO getTransactionById(Long id);
    List<TransactionDTO> getTransactionsByBuyerId(int id);
    List<TransactionDTO> getTransactionsBySellerId(int id);
    void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, int buyerId, int sellerId);

}
