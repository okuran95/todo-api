package com.todo.api.controller;


import com.todo.api.util.JwtUtil;
import com.todo.api.business.UserService;
import com.todo.api.constant.Constants;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.LoginRequest;
import com.todo.api.model.request.RegisterRequest;
import com.todo.api.model.response.RegisterResponse;
import com.todo.api.model.response.ResponseObject;
import com.todo.api.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "Gets jwt token with username and password", description = "user must exist")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(Constants.INVALID_USERNAME_OR_PASSWORD));

        String token = jwtUtil.createToken(user);

        return ResponseObject.createResponse(
                Constants.MESSAGE_LOGIN,
                HttpStatus.OK,
                token
        );
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "email must unique")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse response = authService.register(request);

        return ResponseObject.createResponse(
                Constants.MESSAGE_CREATED,
                HttpStatus.CREATED,
                response
        );
    }

}