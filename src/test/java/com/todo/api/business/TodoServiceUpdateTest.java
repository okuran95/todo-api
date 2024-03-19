package com.todo.api.business;

import com.todo.api.model.entity.Todo;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.TodoRequest;
import com.todo.api.model.response.TodoResponse;
import com.todo.api.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceUpdateTest {
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
        todoService = new TodoService(todoRepository, userService);

    }

    /*
        @Test
        void testUpdateTodo() {
            // Arrange
            String userId = "user123";
            String todoId = "todo123";
            String email = "user@example.com";
            java.time.Duration duration = java.time.Duration.ofHours(1).plusMinutes(30);

            User user = new User(email, "fisrtname", "lastname", "password");
            TodoRequest request = new TodoRequest("Updated Title", "Updated Description", LocalDateTime.now().plusHours(1), duration, 0);

            java.time.Duration oldDuration = java.time.Duration.ofHours(0).plusMinutes(30);
            Todo existingTodo = new Todo("Old Title", "Old Description", LocalDateTime.now(),
                    oldDuration, 1, userId);
            existingTodo.setId(todoId);

            when(userService.findUserByEmail(email)).thenReturn(user);
            when(todoRepository.findByIdAndCreatedBy(todoId, userId)).thenReturn(Optional.of(existingTodo));
            when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            TodoResponse updatedTodoResponse = todoService.update(todoId, request, email);

            // Assert
            assertNotNull(updatedTodoResponse);
            assertEquals(request.getTitle(), updatedTodoResponse.getTitle());
            assertEquals(request.getDescription(), updatedTodoResponse.getDescription());
            assertEquals(request.getStartDateTime(), updatedTodoResponse.getStartDateTime());
            assertEquals(request.getDuration(), updatedTodoResponse.getDuration());
            assertEquals(request.getPriority(), updatedTodoResponse.getPriority());
            verify(todoRepository, times(1)).save(any(Todo.class));
        }
    */
    @Test
    void testUpdateTodo_NotFound() {
        // Arrange
        String userId = "user123";
        String todoId = "todo123";
        String email = "user@example.com";
        java.time.Duration duration = java.time.Duration.ofHours(1).plusMinutes(30);

        User user = new User(email, "firstname", "lastname", "password");
        TodoRequest request = new TodoRequest("Updated Title", "Updated Description",
                LocalDateTime.now().plusHours(1), duration, 3);

        when(userService.findUserByEmail(email)).thenReturn(user);
        when(todoRepository.findByIdAndCreatedBy(todoId, userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class,
                () -> todoService.update(todoId, request, email));
        verify(todoRepository, never()).save(any(Todo.class));
    }


}