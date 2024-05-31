package com.uade.tpo.demo.service.transactionDetailsService;
import java.util.List;

import com.uade.tpo.demo.entity.dto.TransactionDetailsDTO;

public interface TransactionDetailsService {
    TransactionDetailsDTO getDetailById(Long id);
    List<TransactionDetailsDTO> getDetailsByTransactionId(Long id);
    List<TransactionDetailsDTO> getDetailsByProductId(int id);
}
