package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.dto.NewProductoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller("/producto")
public class ProductController {

    @PostMapping()
    public void saveNewProduct(@RequestBody NewProductoDTO newProducto) {

    }
}
