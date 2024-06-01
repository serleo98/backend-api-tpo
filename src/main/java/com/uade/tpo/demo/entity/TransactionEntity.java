package com.uade.tpo.demo.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionDetailsEntity> details = new ArrayList<>();

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
