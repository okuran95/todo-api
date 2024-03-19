package com.todo.api.exeption;

public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}