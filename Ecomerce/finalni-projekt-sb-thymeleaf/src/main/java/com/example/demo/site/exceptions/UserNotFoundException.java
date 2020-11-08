package com.example.demo.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException() {
        super();
    }
    
    public UserNotFoundException(String message) {
        super(message);
    }
}