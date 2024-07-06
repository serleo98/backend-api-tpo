package com.uade.tpo.demo.service.checkoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.ChekoutEntity;
import com.uade.tpo.demo.entity.dto.CheckoutDTO;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import com.uade.tpo.demo.repository.db.CheckOutRepository;

@Service
public class CheckoutService implements ICheckOutService{
    @Autowired
    private CheckOutRepository checkoutRepository;
    @Autowired
    private CloudinaryRepository repository;

    @Override
    public void guardarinforme(CheckoutDTO informProblemDTO) {
        ChekoutEntity checkout =  ChekoutEntity.builder()
        .nombre(informProblemDTO.getNombre())
        .apellido(informProblemDTO.getApellido())
        .direccion(informProblemDTO.getDireccion())
        .codigoTarjeta(informProblemDTO.getCodigoTarjeta())
        .codigoSeguridad(informProblemDTO.getCodigoSeguridad()).build();
        checkoutRepository.save(checkout);
        
    }
    
}
