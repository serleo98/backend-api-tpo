package com.uade.tpo.demo.service.userService.impl;

import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.entity.dto.NewUserPBDTO;
import com.uade.tpo.demo.entity.dto.UserDTO;
import com.uade.tpo.demo.entity.dto.UserPBDTO;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import com.uade.tpo.demo.repository.db.ImageEntityRepository;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.repository.rest.pokcetbase.createUser.CreateUserPBRepository;
import com.uade.tpo.demo.repository.rest.pokcetbase.login.LoginPBRepository;
import com.uade.tpo.demo.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginPBRepository loginPBRepository;

    @Autowired
    private CreateUserPBRepository createUserPBRepository;

    @Autowired

    private ImageEntityRepository repository;

    @Autowired
    private CloudinaryRepository cloudinaryRepository;
    @Autowired
    private ImageEntityRepository imageEntityRepository;

    @Transactional
    public void createUser(UserDTO newUser, MultipartFile img) {

        Optional<User> userEntity = userRepository.findByEmail(newUser.getEmail());


        if (userEntity.isEmpty()) {
            String url="";
            if (Objects.isNull(img)) {
                url = cloudinaryRepository.savePhoto(img.getName(), img);
            }

            NewUserPBDTO newUserIdentity = NewUserPBDTO.builder()
                    .email(newUser.getEmail())
                    .password(newUser.getPassword())
                    .username(newUser.getName())
                    .passwordConfirm(newUser.getPassword())
                    .username(newUser.getNick())
                    .emailVisibility(true)
                    .build();

            ImageEntity avatar =ImageEntity.builder()
                    .url(url).build();

            User userToSave = User.builder()
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .phone(newUser.getPhone())
                    .avatar(avatar)
                    .lastname(newUser.getLastname())
                    .build();

            UserPBDTO userPBDTO = createUserPBRepository.execute(newUserIdentity).get();
            userToSave.setIdentityId(userPBDTO.getId());
            imageEntityRepository.saveAndFlush(avatar);
            userRepository.save(userToSave);

        }
    }

    public LoginPBDTO login(String email, String password) {
        return loginPBRepository.execute(email, password).orElseThrow();
    }

    public User getUser(String identityId) {
        return userRepository.findByIdentityId(identityId).orElseThrow();
    }

}
