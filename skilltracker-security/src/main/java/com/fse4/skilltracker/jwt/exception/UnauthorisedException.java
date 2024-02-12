package com.fse4.skilltracker.jwt.exception;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fse4.skilltracker.jwt.model.response.ErrorRes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorisedException extends RuntimeException {
    private ErrorRes errorResponse;

    public UnauthorisedException(String message) {
        super(message);
        errorResponse = new ErrorRes(HttpStatus.UNAUTHORIZED, new Date(),message);
    }
}