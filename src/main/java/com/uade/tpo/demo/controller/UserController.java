package com.uade.tpo.demo.controller;


import com.uade.tpo.demo.entity.dto.UserDTO;
import com.uade.tpo.demo.service.userService.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public ResponseEntity login(String username, String password) {

       return ResponseEntity.ok(service.login(username, password));
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody UserDTO body) {

        service.createUser(body);

        return ResponseEntity.status(HttpStatus.SC_CREATED).build();
    }

}
