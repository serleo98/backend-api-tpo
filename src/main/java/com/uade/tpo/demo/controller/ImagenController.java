package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/imagen")
public class ImagenController {

    @Autowired
    private CloudinaryRepository repository;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadImage(@RequestPart MultipartFile imageFile) {
        log.info("Uploading image");
        String url = repository.savePhoto(imageFile.getName(), imageFile);
        Map<String,String> response = Map.of("url", url);
        return ResponseEntity.ok(response);
    }
}
