package com.uade.tpo.demo.service.userService;

import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.entity.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void createUser(UserDTO newUser, MultipartFile img);
    LoginPBDTO login(String email, String password);

}
