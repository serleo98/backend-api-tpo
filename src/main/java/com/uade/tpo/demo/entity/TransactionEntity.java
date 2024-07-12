package com.uade.tpo.demo.entity;
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

    private float saleValue;
    @ManyToOne
    private Discount discount;
    private float totalValue;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<TransactionDetailsEntity> details;

}
