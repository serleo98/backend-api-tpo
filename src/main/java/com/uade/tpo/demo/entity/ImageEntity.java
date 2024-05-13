package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @UuidGenerator
    private UUID id;
    private String url;
    private String description;

}
