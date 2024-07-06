package com.uade.tpo.demo.service.purchaseService;

import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.PurchaseDTO;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;


    @Transactional
    @Override
    public void purchase(PurchaseDTO purchaseDTO, User buyer) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalPriceWithDiscount = BigDecimal.ZERO;
        List<TransactionDetailsEntity> transactionDetailsEntities = new ArrayList<>();

        purchaseDTO.getProducts().forEach(product -> {
            Optional<ProductoEntity> p = productRepository.findById(product.getProductId());
            if (p.isPresent()) {
                ProductoEntity productEntity = p.get();

                productEntity.getStock().forEach(stock -> {
                    if (stock.getId().equals(product.getStockId())) {
                        if(product.getQuantity() <= 0 && product.getQuantity() > stock.getQuantity()){
                            throw new RuntimeException("Stock not available");
                        }
                        stock.setQuantity(stock.getQuantity() - product.getQuantity());
                        TransactionDetailsEntity transactionDetails = TransactionDetailsEntity.builder()
                                .product(productEntity)
                                .quantity(product.getQuantity())
                                .unitPrice(productEntity.getPrice())
                                .stockAndType(stock)
                                .build();
                        transactionDetailsRepository.save(transactionDetails);
                        transactionDetailsEntities.add(transactionDetails);
                    }
                });

                totalPrice.add(productEntity.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));

                productRepository.save(productEntity);
            }
        });

        TransactionEntity transaction = TransactionEntity.builder()
                .buyer(buyer)
                .date(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .discount(0)
                .saleValue(totalPrice.floatValue())
                .totalValue(totalPriceWithDiscount.floatValue())
                .details(transactionDetailsEntities)
                .build();

        transactionRepository.saveAndFlush(transaction);
    }
}
