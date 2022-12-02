package com.harera.hayatserver.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.harera.hayatserver.model.BaseEntityDto;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active" })
public class UserDto extends BaseEntityDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String username;
}
