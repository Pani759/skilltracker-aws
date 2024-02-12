package com.fse4.skilltracker.jwt.model.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRes {
    HttpStatus httpStatus;
    Date time;
    String message;

}
