package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.TestDTO;
import com.uade.tpo.demo.repository.db.IImageRepository;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.imagenService.imagenService;
import com.uade.tpo.demo.service.productService.IProductService;
import com.uade.tpo.demo.service.userService.UserService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Controller
public class ExampleController {
    @Autowired
    private IProductService productService;

    @Autowired
    private imagenService imagenService;

    @Autowired
    private IImageRepository imagenRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private UserService userService;

    @GetMapping("/ping")
    public ResponseEntity ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/example/post-con-body")
    public ResponseEntity postConBody(@RequestBody TestDTO body) {

        return ResponseEntity.ok(body);

    }

    @PostMapping("/example/post-con-body/{variable}")
    public ResponseEntity postConBody(@RequestBody TestDTO body, @PathVariable String variable) {

        log.info(variable);
        log.info(body.toString());
        return ResponseEntity.ok(body);

    }

    @PostMapping("/example/post-con-body-y-query-param")
    public ResponseEntity postConBodyYQueryParam(@RequestBody TestDTO body, @RequestParam String variable) {

        log.info(variable);
        log.info(body.toString());

        return ResponseEntity.ok(body);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity saveImagenesToProducto(@RequestPart("imageFile") MultipartFile imageFile,
                                    @RequestParam("id") Integer id, @RequestParam("productName") String productName) throws Exception {
        Optional<ProductoEntity> productOpt = productService.getProductById(id);

        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }

        String url = imagenService.saveImagenes(productName,imageFile);
        

        ProductoEntity product = productOpt.get();

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(url);

        List<ImageEntity> images = product.getImage();
        images.add(imageEntity);
        
        imagenRepository.saveAndFlush(imageEntity);
        
        product.setImage(images);
        
        productRepository.saveAndFlush(product);
        
        return ResponseEntity.ok(url);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImagenesToUser(@RequestPart("imageFile") MultipartFile imageFile,
                                                @RequestParam("id") Integer id, @RequestParam("userName") String userName) throws Exception {
        Integer userOpt = userService.getUserById(id);
        
        if (userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        String url = imagenService.saveImagenes(userName, imageFile);
        
        User user = userRepository.getById(id);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(url);

        
        imagenRepository.saveAndFlush(imageEntity);

        user.setImage(imageEntity);
        
        userRepository.saveAndFlush(user);


        return ResponseEntity.ok(url);
    }
    



}
