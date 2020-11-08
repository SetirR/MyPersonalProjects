package com.example.demo.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class CartNotFoundException extends RuntimeException
{
    public CartNotFoundException() {
        super();
    }
    
    public CartNotFoundException(String message) {
        super(message);
    }
}