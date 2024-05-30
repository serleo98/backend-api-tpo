package com.uade.tpo.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j //.CONSOLE LOG 
@Service
public class ImagenService {

    @Autowired
    private CloudinaryRepository imagenRepository;
 
    public String saveImagenes(String imagenNombre,MultipartFile file){
        return imagenRepository.savePhoto(imagenNombre ,file);
        
    }    



}


