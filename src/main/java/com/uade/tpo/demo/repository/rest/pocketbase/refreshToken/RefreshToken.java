package com.uade.tpo.demo.repository.rest.pocketbase.refreshToken;


import com.uade.tpo.demo.entity.dto.LoginPBDTO;

import java.util.Optional;

public interface RefreshToken {

    Optional<LoginPBDTO> execute(String jwt);

}
