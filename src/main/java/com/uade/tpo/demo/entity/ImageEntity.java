package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UuidGenerator;

//import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    //@UuidGenerator
    private Integer id;
    private String url;
    private String description;

}
