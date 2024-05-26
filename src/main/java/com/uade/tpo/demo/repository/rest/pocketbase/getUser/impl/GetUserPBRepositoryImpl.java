package com.uade.tpo.demo.repository.rest.pocketbase.getUser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.tpo.demo.entity.dto.UserPBDTO;
import com.uade.tpo.demo.repository.rest.pocketbase.getUser.GetUserPBRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GetUserPBRepositoryImpl implements GetUserPBRepository {

    public Optional<UserPBDTO> execute(String id, String token) {

        String url = "https://pocketbase-production-94d8.up.railway.app/api/collections/users/records/" + id;

        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Void> request =
                new HttpEntity<>(headers);

        ResponseEntity<String> responseEntityStr = restTemplate
                .exchange(url, HttpMethod.GET, request, String.class);

        try {

            return Optional.of(objectMapper.readValue(responseEntityStr.getBody(), UserPBDTO.class));
        } catch (Exception e) {

        }
        return Optional.empty();
    }
}
