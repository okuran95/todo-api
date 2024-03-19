package com.todo.api.business;

import com.todo.api.constant.Constants;
import com.todo.api.exeption.BusinessException;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.RegisterRequest;
import com.todo.api.model.response.RegisterResponse;
import com.todo.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        boolean isExist = userRepository.existsByEmail(email);
        if (isExist) {
            throw new BusinessException(Constants.EMAIL_ALREADY_EXIST);
        }

        String password = registerRequest.getPassword();
        String validPassword = registerRequest.getValidPassword();
        if (!Objects.equals(password, validPassword)) {
            throw new RuntimeException(Constants.PASSWORDS_ARE_NOT_EQUAL);
        }

        password = passwordEncoder.encode(password);

        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();

        User user = new User(email, firstName, lastName, password);
        user = userRepository.save(user);
        return new RegisterResponse(user);
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            throw new BusinessException(Constants.EMAIL_CAN_NOT_BE_NULL);
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(Constants.USER_NOT_FOUND));

    }
}
