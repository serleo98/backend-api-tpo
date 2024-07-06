package com.uade.tpo.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;


@Data
@Entity
@Builder
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "details_id_seq", sequenceName = "details_id_seq")
public class TransactionDetailsEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ProductoEntity product;

    private BigDecimal unitPrice;
    private Integer quantity;

    @ManyToOne
    private StockAndType stockAndType;


}
