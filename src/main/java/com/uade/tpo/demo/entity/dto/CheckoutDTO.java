package com.uade.tpo.demo.entity.dto;


import lombok.Builder;
import lombok.Data;


@Builder

@Data
public class CheckoutDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String direccion;
    private int codigoTarjeta;
    private int codigoSeguridad;
    private String nombreDeLaTarjeta;
}
