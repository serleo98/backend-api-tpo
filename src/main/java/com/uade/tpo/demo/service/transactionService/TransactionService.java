package com.uade.tpo.demo.service.transactionService;

import java.util.Date;
import java.util.List;
import com.uade.tpo.demo.entity.dto.TransactionDTO;

public interface TransactionService {
    
    TransactionDTO getTransactionById(Long id);
    List<TransactionDTO> getTransactionsByBuyerId(Integer id);
    List<TransactionDTO> getTransactionsBySellerId(Integer id);
    void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount);

}
