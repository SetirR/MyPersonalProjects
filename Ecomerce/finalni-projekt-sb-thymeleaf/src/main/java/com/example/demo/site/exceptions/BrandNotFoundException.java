package com.example.demo.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class BrandNotFoundException extends RuntimeException
{
    public BrandNotFoundException() {
        super();
    }
    
    public BrandNotFoundException(String message) {
        super(message);
    }
}