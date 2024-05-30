package com.uade.tpo.demo.controller;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.UserDTO;
import com.uade.tpo.demo.repository.rest.pokcetbase.refresh.RefreshToken;
import com.uade.tpo.demo.service.userService.UserService;
import com.uade.tpo.demo.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller("/user")
public class UsersController {

    @Autowired
    private UserService service;

    @Autowired
    private RefreshToken refreshToken;

    @PostMapping("/login")
    public ResponseEntity login(String username, String password) {

        return ResponseEntity.ok(service.login(username, password));
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody UserDTO body) {

        service.createUser(body);

        return ResponseEntity.status(HttpStatus.SC_CREATED).build();
    }

    @SecurityRequirement(name = "bearer")
    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestParam String jwt) {

        //TODO: EJEMPLO DE OBTENER LOS DATOS DE LA SESION ACTUAL
        User currentUser = AuthUtils.getCurrentAuthUser(User.class);

        return ResponseEntity.ok(refreshToken.execute(jwt));

    }
}
