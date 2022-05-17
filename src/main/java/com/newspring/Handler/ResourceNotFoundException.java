package com.newspring.Handler;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends Exception{

    private static final long serialUID = 1L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
