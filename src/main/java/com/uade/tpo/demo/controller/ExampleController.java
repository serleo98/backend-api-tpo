package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.dto.TestDTO;
import com.uade.tpo.demo.service.ImagenService;
import com.uade.tpo.demo.entity.dto.ImagenDTO;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;


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
    private ImagenService imagenService;
    
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
    public ResponseEntity saveImagenes(@RequestPart("imageFile") MultipartFile imageFile,
                                    @RequestParam("title") String title) throws Exception {


        String url = imagenService.saveImagenes(title,imageFile);

        return ResponseEntity.ok(url);
    }
}
