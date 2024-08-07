package com.uade.tpo.demo.service.purchaseService;

import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.PurchaseDTO;
import com.uade.tpo.demo.repository.db.DiscountRepository;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private DiscountRepository discountRepository;


    @Transactional
    @Override
    public void purchase(PurchaseDTO purchaseDTO, User buyer) {

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalPriceWithDiscount = new AtomicReference<>(BigDecimal.ZERO);
        List<TransactionDetailsEntity> transactionDetailsEntities = new ArrayList<>();

        // Validar y actualizar el stock de cada producto
        purchaseDTO.getProducts().forEach(product -> {
            Optional<ProductoEntity> p = productRepository.findById(product.getProductId());
            if (p.isPresent()) {
                ProductoEntity productEntity = p.get();
                if (productEntity.getStock() <= 0 || product.getQuantity() > productEntity.getStock()) {
                    throw new RuntimeException("Stock not available");
                }
                productEntity.setStock(productEntity.getStock() - product.getQuantity());
                productRepository.save(productEntity);
            } else {
                throw new RuntimeException("Product not found");
            }
        });

        // Procesar cada producto y calcular el precio total
        purchaseDTO.getProducts().forEach(product -> {
            Optional<ProductoEntity> p = productRepository.findById(product.getProductId());
            if (p.isPresent()) {
                ProductoEntity productEntity = p.get();

                TransactionDetailsEntity transactionDetails = TransactionDetailsEntity.builder()
                        .product(productEntity)
                        .quantity(product.getQuantity())
                        .unitPrice(productEntity.getPrice())
                        .build();
                transactionDetailsRepository.save(transactionDetails);
                transactionDetailsEntities.add(transactionDetails);

                totalPrice.updateAndGet(v -> v.add(productEntity.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()))));
            }
        });

        // Aquí puedes aplicar el descuento si es necesario
        // totalPriceWithDiscount.set(totalPrice.get().subtract(descuento));

        Discount discount = null;
        if (StringUtils.isNotBlank(purchaseDTO.getDiscountCode())) {
            Optional<Discount> discountOpt = discountRepository.findById(UUID.fromString(purchaseDTO.getDiscountCode()));
            if (discountOpt.isPresent()) {
                discount = discountOpt.get();
                totalPriceWithDiscount.set(totalPrice.get().subtract(totalPrice.get().multiply(discountOpt.get().getValue().divide(BigDecimal.valueOf(100)))));
            }
        } else {
            totalPriceWithDiscount.set(totalPrice.get());
        }

        TransactionEntity transaction;
        if(discount != null) {
            transaction= TransactionEntity.builder()
                    .buyer(buyer)
                    .date(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                    .discount(discount) // Ajustar esto si hay algún descuento
                    .saleValue(totalPrice.get().floatValue())
                    .totalValue(totalPriceWithDiscount.get().floatValue())
                    .details(transactionDetailsEntities)
                    .build();
        } else {
            transaction = TransactionEntity.builder()
                    .buyer(buyer)
                    .date(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                    .saleValue(totalPrice.get().floatValue())
                    .totalValue(totalPriceWithDiscount.get().floatValue())
                    .details(transactionDetailsEntities)
                    .build();
        }

        transactionRepository.saveAndFlush(transaction);
    }

}
