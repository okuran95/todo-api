package com.todo.api.business;

import com.todo.api.constant.Constants;
import com.todo.api.exeption.BusinessException;
import com.todo.api.model.entity.Todo;
import com.todo.api.model.entity.User;
import com.todo.api.model.request.TodoCriteria;
import com.todo.api.model.request.TodoRequest;
import com.todo.api.model.response.TodoResponse;
import com.todo.api.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;

    public TodoResponse save(TodoRequest request, String email) {
        LocalDateTime startDateTime = request.getStartDateTime();

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(Constants.START_DATE_TIME_CAN_NOT_BEFORE_NOW);
        }

        User user = userService.findUserByEmail(email);

        Todo todo = new Todo(
                request.getTitle(),
                request.getDescription(),
                request.getStartDateTime(),
                request.getDuration(),
                request.getPriority(),
                user.getId());

        todo = todoRepository.save(todo);
        return new TodoResponse(todo);

    }

    public TodoResponse update(String id, TodoRequest request, String email) {
        User user = userService.findUserByEmail(email);

        Todo todo = todoRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.TODO_NOT_FOUND));

        LocalDateTime startDateTime = todo.getStartDateTime();
        LocalDateTime newStartDateTime = request.getStartDateTime();

        if (!startDateTime.equals(newStartDateTime) &&
                newStartDateTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(Constants.START_DATE_TIME_CAN_NOT_BEFORE_NOW);
        }

        todo.update(
                request.getTitle(),
                request.getDescription(),
                newStartDateTime,
                request.getDuration(),
                request.getPriority());

        todo = todoRepository.save(todo);
        return new TodoResponse(todo);
    }

    public void delete(String id, String email) {
        User user = userService.findUserByEmail(email);

        Todo todo = todoRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.TODO_NOT_FOUND));

        todoRepository.delete(todo);
    }

    public List<TodoResponse> findAllByCriteria(TodoCriteria todoCriteria, String email) {
        User user = userService.findUserByEmail(email);

        List<Todo> todos = todoRepository.findByCriteria(
                user.getId(),
                todoCriteria.getStartDate(),
                todoCriteria.getEndDate(),
                todoCriteria.getStatus(),
                todoCriteria.getQuery(),
                todoCriteria.getPriority());

        return todos.stream().map(TodoResponse::new).toList();
    }

    public TodoResponse findById(String id, String email) {
        User user = userService.findUserByEmail(email);

        Todo todo = todoRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.TODO_NOT_FOUND));

        return new TodoResponse(todo);
    }

    public TodoResponse complete(String id, String email) {
        User user = userService.findUserByEmail(email);

        Todo todo = todoRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.TODO_NOT_FOUND));

        todo.complete();
        todo = todoRepository.save(todo);
        return new TodoResponse(todo);
    }
}
