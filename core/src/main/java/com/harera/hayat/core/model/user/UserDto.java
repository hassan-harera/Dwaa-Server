package com.harera.hayat.core.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.harera.hayat.core.model.BaseEntityDto;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active", "password" })
public class UserDto extends BaseEntityDto {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
    private String username;
}
