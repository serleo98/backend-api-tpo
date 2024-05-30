package com.uade.tpo.demo.service.userService;

import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.entity.dto.UserDTO;

public interface UserService {

    void createUser(UserDTO newUser);
    LoginPBDTO login(String email, String password);

}
