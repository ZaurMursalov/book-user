package com.example.userms.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ExceptionResponse handle(Exception ex) {
//        log.info("Exception:", ex);
//        return new ExceptionResponse(ExceptionConstants.UNEXPECTED_EXCEPTION_CODE, ExceptionConstants.UNEXPECTED_EXCEPTION_MESSAGE);
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND )
    public ExceptionResponse handle(NotFoundException ex) {
        log.info("NotFoundException:", ex);
        return new ExceptionResponse(ex.getCode(), ex.getMessage());

    }

}
