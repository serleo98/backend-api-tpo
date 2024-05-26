package com.uade.tpo.demo.service.userService.impl;

import com.uade.tpo.demo.entity.UserEntity;
import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.entity.dto.NewUserPBDTO;
import com.uade.tpo.demo.entity.dto.UserDTO;
import com.uade.tpo.demo.entity.dto.UserPBDTO;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.repository.rest.pocketbase.createUser.CreateUserPBRepository;
import com.uade.tpo.demo.repository.rest.pocketbase.login.LoginPBRepository;
import com.uade.tpo.demo.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginPBRepository loginPBRepository;

    @Autowired
    private CreateUserPBRepository createUserPBRepository;

    public void createUser(UserDTO newUser) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(newUser.getEmail());

        if(userEntity.isEmpty()){

            NewUserPBDTO newUserIdentity = NewUserPBDTO.builder()
                    .email(newUser.getEmail())
                    .password(newUser.getPassword())
                    .username(newUser.getName())
                    .passwordConfirm(newUser.getPassword())
                    .username(newUser.getNick())
                    .emailVisibility(true)
                    .build();

            UserEntity userToSave = UserEntity.builder()
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .phone(newUser.getPhone())
                    .lastname(newUser.getLastname())
                    .build();

            UserPBDTO userPBDTO = createUserPBRepository.execute(newUserIdentity).get();
            userToSave.setIdentityId(userPBDTO.getId());
            userRepository.saveAndFlush(userToSave);
        }
    }

    public LoginPBDTO login(String email, String password) {
        return loginPBRepository.execute(email,password).orElseThrow();
    }
}
