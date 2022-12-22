package com.nikhil.hotelservice.exceptions;

import org.springframework.stereotype.Service;

@Service
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
    public ResourceNotFoundException(){
        super("Resource not found");
    }
}
