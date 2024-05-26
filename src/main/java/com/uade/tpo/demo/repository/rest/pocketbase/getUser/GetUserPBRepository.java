package com.uade.tpo.demo.repository.rest.pocketbase.getUser;

import com.uade.tpo.demo.entity.dto.UserPBDTO;

import java.util.Optional;

public interface GetUserPBRepository {

    Optional<UserPBDTO> execute(String id, String token);
}
