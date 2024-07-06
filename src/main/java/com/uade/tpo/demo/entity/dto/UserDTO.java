package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String nick;
    private String name;
    private String password;
    private String lastname;
    private String email;
    private String phone;

}
