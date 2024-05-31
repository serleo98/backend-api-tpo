package com.uade.tpo.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.Builder;

import java.util.Date;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Date date;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User buyer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User seller;
    private float saleValue;
    private float discount;
    private float totalValue;

    public Long getId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public String getBuyerId(){
        return buyer.getIdentityId();
    }

    public String getSellerId(){
        return seller.getIdentityId();
    }
    public float getSaleValue(){
        return saleValue;
    }

    public float getDiscount(){
        return discount;
    }

    public float getTotalValue(){
        return totalValue;
    }

}
