package com.bee.store.config;

import com.bee.store.dtos.ApiError;
import com.bee.store.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handlerNotFound(HttpServletRequest req, NotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<ApiError> handlerUsernameNotFoundException(HttpServletRequest req, RuntimeException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
