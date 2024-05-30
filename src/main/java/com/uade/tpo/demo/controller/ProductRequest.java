package com.uade.tpo.demo.controller;

import lombok.Data;

@Data
public class ProductRequest {
    private int id;
    private String name;
    private int price;
    private String description;
}
