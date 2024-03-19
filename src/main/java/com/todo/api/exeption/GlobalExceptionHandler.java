package com.todo.api.exeption;

import com.todo.api.constant.Constants;
import com.todo.api.model.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseObject.createResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBusinessExceptions(BusinessException exception) {
        return ResponseObject.createResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseObject.createResponse(
                Constants.INVALID_USERNAME_OR_PASSWORD,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({NoAuthException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleBusinessExceptions(NoAuthException exception) {
        return ResponseObject.createResponse(
                Constants.NO_AUTH,
                HttpStatus.UNAUTHORIZED
        );
    }
}
