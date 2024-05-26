package com.uade.tpo.demo.repository.rest.pocketbase.login.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.uade.tpo.demo.entity.dto.LoginPBDTO;
import com.uade.tpo.demo.repository.rest.pocketbase.login.LoginPBRepository;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class LoginPBRepositoryImpl implements LoginPBRepository {

    @Autowired
    private Gson gson;

    /**
     * user puede ser email o username
     * @param user
     * @param password
     * @return
     */
    public Optional<LoginPBDTO> execute(String user, String password) {

        String url = "https://pocketbase-production-94d8.up.railway.app/api/collections/users/auth-with-password";

        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("identity", user);
        personJsonObject.put("password", password);

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(),headers);

        ResponseEntity<String> responseEntityStr = restTemplate
                .exchange(url, HttpMethod.POST, request, String.class);

        try {

            return Optional.of(gson.fromJson(responseEntityStr.getBody(), LoginPBDTO.class));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
