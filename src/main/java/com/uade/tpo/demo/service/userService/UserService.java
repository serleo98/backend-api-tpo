package com.uade.tpo.demo.service.userService;

import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.entity.dto.UserDTO;

public interface UserService {

    void createUser(UserDTO newUser);
    LoginPBDTO login(String email, String password);
    Optional<User> getUserById(Integer userId);
}
