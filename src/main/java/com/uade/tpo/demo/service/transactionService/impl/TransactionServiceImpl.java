package com.uade.tpo.demo.service.transactionService.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.TransactionDetailsEntity;
import com.uade.tpo.demo.entity.TransactionEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.TransactionDTO;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.TransactionDetailsRepository;
import com.uade.tpo.demo.repository.db.TransactionRepository;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.exceptions.TransactionNotFoundException;
import com.uade.tpo.demo.service.exceptions.UserNotFoundException;
import com.uade.tpo.demo.service.transactionService.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    public TransactionDTO getTransactionById(Long id) {
        Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);

        if (!transactionEntity.isEmpty()) {
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .id(transactionEntity.get().getId())
                    .date(transactionEntity.get().getDate())
                    .buyerId(transactionEntity.get().getBuyer())
                    .saleValue(transactionEntity.get().getSaleValue())
                    .discount((Objects.isNull(transactionEntity.get().getDiscount())
                            ? (float) 0
                            : transactionEntity.get().getDiscount().getValue().floatValue()))
                    .totalValue(transactionEntity.get().getTotalValue())
                    .details(transactionEntity.get().getDetails())
                    .build();
            return transactionDTO;
        } else {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
    }

    @Override
    public List<TransactionDTO> getTransactionsByBuyerId(Integer id) {
        Optional<User> buyer = userRepository.findById(id);
        if (buyer.isEmpty()) {
            throw new UserNotFoundException("User with id" + id + "not found");
        }
        List<TransactionEntity> transactionEntity = transactionRepository.findByBuyer(buyer.get());
        List<TransactionDTO> transactions = new ArrayList<>();
        for (TransactionEntity t : transactionEntity) {
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .id(t.getId())
                    .date(t.getDate())
                    .buyerId(t.getBuyer())
                    .saleValue(t.getSaleValue())
                    .discount((Objects.isNull(t.getDiscount())
                            ? (float) 0
                            : t.getDiscount().getValue().floatValue()))
                    .totalValue(t.getTotalValue())
                    .details(t.getDetails())
                    .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public List<TransactionDTO> getTransactionsBySellerId(Integer id) {
        Optional<User> seller = userRepository.findById(id);
        if (seller.isEmpty()) {
            throw new UserNotFoundException("User with id" + id + "not found");
        }
        List<TransactionEntity> transactionEntity = transactionRepository.findBySeller(seller.get().getId());
        List<TransactionDTO> transactions = new ArrayList<>();
        for (TransactionEntity t : transactionEntity) {
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .id(t.getId())
                    .date(t.getDate())
                    .buyerId(t.getBuyer())
                    .saleValue(t.getSaleValue())
                    .discount((Objects.isNull(t.getDiscount())
                    ? (float) 0
                    : t.getDiscount().getValue().floatValue()))
                    .totalValue(t.getTotalValue())
                    .details(t.getDetails())
                    .build();
            transactions.add(transactionDTO);
        }
        return transactions;
    }

    @Override
    public void createTransaction(Date date, List<Integer> productsId, List<Integer> quantities, Integer buyerId, float discount) {

        // Verificar que las listas de productos y cantidades tengan el mismo tamaño
        if (productsId.size() != quantities.size()) {
            throw new IllegalArgumentException("La lista de productos y cantidades debe tener el mismo tamaño");
        }

        //busco el comprador y el vendedor
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("Comprador no encontrado"));

        //armo la transaccion, para obtener su id y luego armar los details con este
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .date(date)
                .buyer(buyer)
                //.discount()
                .build();


        //Armo todas las entradas en TransactionDetails
        float sum = 0;
        for (int i = 0; i < productsId.size(); i++) {
            int productId = productsId.get(i);
            int quantity = quantities.get(i);
            Optional<ProductoEntity> productEntity = productRepository.findById(productId);
            TransactionDetailsEntity transactionDetail = TransactionDetailsEntity.builder()
                    .product(productEntity.get())
                    .unitPrice(productEntity.get().getPrice())
                    .quantity(quantity)
                    .build();

            sum += (productEntity.get().getPrice().floatValue() * quantity);
            transactionDetailsRepository.save(transactionDetail);
            transactionEntity.getDetails().add(transactionDetail);
        }

        transactionEntity.setSaleValue(sum);
        transactionEntity.setTotalValue(sum * discount / 100);
        transactionRepository.save(transactionEntity);
    }

}
