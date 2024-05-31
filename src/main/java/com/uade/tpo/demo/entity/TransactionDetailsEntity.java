package com.uade.tpo.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Builder;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;


@Data
@Entity
@Builder
@Table(name = "transactionsDetails")
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private TransactionEntity transaction;

    @ManyToMany
    @JoinColumn(referencedColumnName = "id")
    private ProductoEntity product;

    private BigDecimal unitPrice;
    private Integer quantity;
    

    public Long getId(){
        return id;
    }

    public TransactionEntity getTransaction(){
        return transaction;
    }

    public ProductoEntity getProduct(){
        return product;
    }

    public BigDecimal getUnitPrice(){
        return unitPrice;
    }

    public Integer getQuantity(){
        return quantity;
    }

}
