package com.uade.tpo.demo.service.imagenService;

import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImagenService {

    @Autowired
    private CloudinaryRepository cloudinaryRepository;

    public String saveImagenes(String imagenNombre, MultipartFile file){
        return cloudinaryRepository.savePhoto(imagenNombre ,file);

    }
}
