package com.example.scoutsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Member(s) not Found")
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String exception) {
        super(exception);
    }
}
