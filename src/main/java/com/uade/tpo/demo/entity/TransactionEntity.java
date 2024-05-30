package com.uade.tpo.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User buyer;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User seller;

    public Long getId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public Long getCartId(){
        return cart.getId();
    }

    public int getBuyerId(){
        return buyer.getId();
    }

    public int getSellerId(){
        return seller.getId();
    }

}
