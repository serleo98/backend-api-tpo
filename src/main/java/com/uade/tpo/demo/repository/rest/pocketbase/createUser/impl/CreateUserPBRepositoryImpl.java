package com.uade.tpo.demo.repository.rest.pocketbase.createUser.impl;

import com.google.gson.Gson;
import com.uade.tpo.demo.entity.dto.NewUserPBDTO;
import com.uade.tpo.demo.entity.dto.UserPBDTO;
import com.uade.tpo.demo.repository.rest.pocketbase.createUser.CreateUserPBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CreateUserPBRepositoryImpl implements CreateUserPBRepository {

    private static final Logger log = LoggerFactory.getLogger(CreateUserPBRepositoryImpl.class);
    @Autowired
    private Gson gson;


    public Optional<UserPBDTO> execute(NewUserPBDTO newUserPBDTO) {

        String url = "https://pocketbase-production-94d8.up.railway.app/api/collections/users/records";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(gson.toJson(newUserPBDTO), headers);


        try {

            ResponseEntity<String> responseEntityStr = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);

            return Optional.of(gson.fromJson(responseEntityStr.getBody(), UserPBDTO.class));

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.empty();
    }
}
