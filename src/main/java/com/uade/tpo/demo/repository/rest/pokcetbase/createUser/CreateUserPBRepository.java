package com.uade.tpo.demo.repository.rest.pokcetbase.createUser;

import com.uade.tpo.demo.entity.dto.NewUserPBDTO;
import com.uade.tpo.demo.entity.dto.UserPBDTO;

import java.util.Optional;

public interface CreateUserPBRepository {
    Optional<UserPBDTO> execute(NewUserPBDTO newUserPBDTO);
}
