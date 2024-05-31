package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.dto.TestDTO;
import com.uade.tpo.demo.service.productService.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Controller
public class ExampleController {

    @Autowired
    private ProductService productService;

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

    /**
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
    }*/
}
