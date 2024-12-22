package com.leaning.linkedin.user.dto;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String name;
    private String email;
    private String password;
}
