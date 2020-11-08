package com.example.demo.site.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand cant be deleted")
public class BrandCantBeDeletedException extends RuntimeException
{
    public BrandCantBeDeletedException() {
        super();
    }
    
    public BrandCantBeDeletedException(String message) {
        super(message);
    }

    public BrandCantBeDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}