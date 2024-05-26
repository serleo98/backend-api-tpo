package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserPBDTO {

    private String username;
    private String email;
    private boolean emailVisibility;
    private String password;
    private String passwordConfirm;
    private String name;

}
