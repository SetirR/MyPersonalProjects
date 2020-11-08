package com.example.demo.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class OrderNotFoundException extends RuntimeException
{
    public OrderNotFoundException() {
        super();
    }
    
    public OrderNotFoundException(String message) {
        super(message);
    }
}