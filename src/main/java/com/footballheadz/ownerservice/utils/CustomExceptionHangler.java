package com.footballheadz.ownerservice.utils;

import com.footballheadz.ownerservice.exception.InvalidRequestException;
import com.footballheadz.ownerservice.exception.MissingHeaderInfoException;
import com.footballheadz.ownerservice.exception.RecordNotFoundException;
import com.footballheadz.ownerservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHangler extends ResponseEntityExceptionHandler {

    private String INCORRECT_REQUEST = "INCORRECT_REQUEST";
    private String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request)
    {
        return getErrorResponseResponseEntity(ex.getLocalizedMessage(), INCORRECT_REQUEST, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingHeaderInfoException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
            (MissingHeaderInfoException ex, WebRequest request) {
        return getErrorResponseResponseEntity(ex.getLocalizedMessage(), BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidRequestException
            (InvalidRequestException ex, WebRequest request) {
        return getErrorResponseResponseEntity(ex.getLocalizedMessage(), BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(String localizedMessage, String bad_request, HttpStatus badRequest) {
        List<String> details = new ArrayList<>();
        details.add(localizedMessage);
        ErrorResponse error = new ErrorResponse(bad_request, details);
        return new ResponseEntity<>(error, badRequest);
    }
}
