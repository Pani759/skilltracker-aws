package com.fse4.skilltracker.jwt.model.response;

import lombok.Data;

@Data
public class LoginRes {
    private String email;
    private String token;

}
