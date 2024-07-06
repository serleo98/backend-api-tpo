package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import static jakarta.persistence.GenerationType.IDENTITY;
@Builder
@Data
@Entity
public class ChekoutEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String direccion;
    private int codigoTarjeta;
    private int codigoSeguridad;
    private String nombreDeLaTarjeta;


}
