package com.footballheadz.ownerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid Request")
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public InvalidRequestException(String message) {
        super(message);
    }
}
