package com.uade.tpo.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j //.CONSOLE LOG 
@Service
public class ImagenService {

    @Autowired
    private CloudinaryRepository imagenRepository;

    public void saveImagenes(String imagen){
        imagenRepository.savePhoto(imagen, null);
        
        if (StringUtils.isBlank(imagen)) {
            throw new Exception("Image not valid");
        }
        
        try {
            // Split the base64 string to separate the data from base64 header
            String[] strings = imagen.split(",");
            //convert base64 string to binary
            byte[] data = Base64.getDecoder().decode(strings[0]);
            ByteArrayResource resource = new ByteArrayResource(data);
            return resource;
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new InternalException("Image not found", "Could not find image with id.", imgId);
        }

    }    


   

}


