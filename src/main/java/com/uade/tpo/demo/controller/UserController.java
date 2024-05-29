package com.uade.tpo.demo.controller;


import com.uade.tpo.demo.entity.dto.UserDTO;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import com.uade.tpo.demo.repository.rest.pocketbase.refreshToken.RefreshToken;
import com.uade.tpo.demo.service.userService.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Controller("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RefreshToken refreshToken;

    @Autowired
    private CloudinaryRepository imgRep;

    @PostMapping("/login")
    public ResponseEntity login(String username, String password) {

       return ResponseEntity.ok(service.login(username, password));
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody UserDTO body) {

        service.createUser(body);

        return ResponseEntity.status(HttpStatus.SC_CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestParam String jwt) {

        return ResponseEntity.ok(refreshToken.execute(jwt));
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity saveImage(@RequestPart("imageFile") MultipartFile imageFile,
                                    @RequestParam("title") String title) throws Exception {


        String url = imgRep.savePhoto(title,imageFile);

        return ResponseEntity.ok(url);
    }

}
