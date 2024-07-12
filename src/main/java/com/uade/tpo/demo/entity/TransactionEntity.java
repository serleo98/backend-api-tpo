package com.uade.tpo.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
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

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "id")
    private Discount discount ;
    private float totalValue;

    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<TransactionDetailsEntity> details;

}
