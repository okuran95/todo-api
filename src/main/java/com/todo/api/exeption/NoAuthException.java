package com.todo.api.exeption;

public class NoAuthException extends Exception {
    public NoAuthException(String message){
        super(message);
    }
}