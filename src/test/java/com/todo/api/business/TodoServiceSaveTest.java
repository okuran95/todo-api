package com.todo.api.business;

import com.todo.api.constant.Constants;
import com.todo.api.constant.TokenProperties;
import com.todo.api.exeption.BusinessException;
import com.todo.api.model.entity.Todo;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.TodoRequest;
import com.todo.api.model.response.TodoResponse;
import com.todo.api.repository.TodoRepository;
import com.todo.api.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

public class TodoServiceSaveTest {

    @Mock
    private TodoService todoService;
    @Mock
    private UserService userService;
    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoService(todoRepository,userService);

    }

    @Test
    public void testSaveTodo_Success() {
        String email = "test@example.com";
        User user = new User(email,"ad","soyad","password");
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);
        java.time.Duration duration = java.time.Duration.ofHours(1).plusMinutes(30);
        TodoRequest request = new TodoRequest("Test Todo", "Description", startDateTime, duration, 2);
        when(userService.findUserByEmail(email)).thenReturn(user);
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo todo = invocation.getArgument(0);
            todo.setId("123456789");
            return todo;
        });

        // Act
        TodoResponse response = todoService.save(request,email);

        // Assert
        assertNotNull(response);
        assertEquals("Test Todo", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals(startDateTime, response.getStartDateTime());
        assertEquals(duration, response.getDuration());
        assertEquals(2, response.getPriority());
        assertEquals(user.getId(), response.getCreatedBy());
    }

    @Test
    public void testSaveTodo_StartDateTimeBeforeNow_ThrowsBusinessException() {
        UserService userService = mock(UserService.class);
        TodoRepository todoRepository = mock(TodoRepository.class);

        TodoService todoService = new TodoService(todoRepository,userService);

        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1);
        java.time.Duration duration = java.time.Duration.ofHours(1).plusMinutes(30);
        TodoRequest request = new TodoRequest("Test Todo", "Description", startDateTime, duration, 0);

        BusinessException exception = assertThrows(BusinessException.class, () -> todoService.save(request,"test@example.com"));
        assertEquals(Constants.START_DATE_TIME_CAN_NOT_BEFORE_NOW, exception.getMessage());
    }
}
