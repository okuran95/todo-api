package com.todo.api.model.response;

import com.todo.api.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterResponse {
    private final String username;
    private final LocalDateTime createdDate;

    public RegisterResponse(User user){
        this.username = user.getEmail();
        this.createdDate = user.getCreatedDate();
    }
}
