package com.uade.tpo.demo.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class CartEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public Long getId(){
        return this.id;
    }
}
