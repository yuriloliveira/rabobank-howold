package com.rabobank.assignment.howold.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;

@ControllerAdvice
@Slf4j
public class ErrorController {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> invalidRequestExceptionHandler(InvalidRequestException ex) {
        log.error("The request is not valid", ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> serverletExceptionHandler(Exception ex) {
        log.error("The request could not be processed due to an exception", ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse("An unexpected error occurred"));
    }
}
