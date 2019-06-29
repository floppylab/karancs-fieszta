package com.floppylab.karancsfieszta.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice(basePackages = "com.floppylab")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedExceptionError(Exception ex) {
        ApiError apiError = new ApiError(new Date(), ex.getMessage());
        log.error("Error happened:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AnimationNotFoundException.class})
    public ResponseEntity<Object> handleAnimationNotFoundExceptionError(Exception ex) {
        ApiError apiError = new ApiError(new Date(), ex.getMessage());
        log.error("Error happened:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptionError(Exception ex) {
        ApiError apiError = new ApiError(new Date(), ex.getMessage());
        log.error("Error happened:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
