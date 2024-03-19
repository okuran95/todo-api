package com.todo.api.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.todo.api.business.UserService;
import com.todo.api.constant.Constants;
import com.todo.api.exeption.BusinessException;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.RegisterRequest;
import com.todo.api.model.response.RegisterResponse;
import com.todo.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testRegister_Success() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);

        User user = new User("test@example.com", "john", "doe", "password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "john", "doe", "password", "password");

        UserService userService = new UserService(userRepository, passwordEncoder);

        RegisterResponse registerResponse = userService.register(registerRequest);

        verify(userRepository, times(1)).save(any(User.class));

        assertNotNull(registerResponse);
        assertEquals("test@example.com", registerResponse.getUsername());

    }

    @Test
    public void testRegister_EmailAlreadyExists() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        RegisterRequest registerRequest = new RegisterRequest("existing@example.com", "password", "password", "John", "Doe");

        UserService registerService = new UserService(userRepository, null);

        assertThrows(BusinessException.class, () -> {
            registerService.register(registerRequest);
        }, Constants.EMAIL_ALREADY_EXIST);
    }

    @Test
    public void testRegister_PasswordsNotEqual() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password", "password2", "John", "Doe");

        UserService registerService = new UserService(null, null);

        assertThrows(RuntimeException.class, () -> {
            registerService.register(registerRequest);
        }, Constants.PASSWORDS_ARE_NOT_EQUAL);
    }
}
