package com.uade.tpo.demo.entity;

import java.util.Arrays;

public enum SexEnum {
    UNISEX,
    FEM,
    MAS;

    public static SexEnum getByName(String name) {
        return Arrays.stream(SexEnum.values())
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }
}
