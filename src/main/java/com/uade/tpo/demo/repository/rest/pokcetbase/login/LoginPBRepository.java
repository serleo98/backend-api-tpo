package com.uade.tpo.demo.repository.rest.pokcetbase.login;

import com.uade.tpo.demo.entity.dto.LoginPBDTO;

import java.util.Optional;

public interface LoginPBRepository {
    Optional<LoginPBDTO> execute(String user, String password);
}
