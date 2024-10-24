package com.isa2024.isa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends RuntimeException{

    //private static final long serialVersionUID = 1L;

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
