package com.todo.api.controller;


import com.todo.api.util.JwtUtil;
import com.todo.api.business.TodoService;
import com.todo.api.constant.Constants;
import com.todo.api.model.request.TodoCriteria;
import com.todo.api.model.request.TodoRequest;
import com.todo.api.model.response.ResponseObject;
import com.todo.api.model.response.TodoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/todos")
@Tag(name = "Todo Controller")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final JwtUtil jwtUtil;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all todos as list by criteria values", description = "only for auth user")
    public ResponseEntity<Object> getAll(TodoCriteria todoCriteria,
                                         @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        List<TodoResponse> responses = todoService.findAllByCriteria(todoCriteria, email);

        return ResponseObject.createResponse(
                Constants.MESSAGE_FOUND,
                HttpStatus.OK,
                responses
        );
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Return todo by id", description = "todo must exist")
    public ResponseEntity<Object> getById(@PathVariable String id,
                                          @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        TodoResponse response = todoService.findById(id, email);

        return ResponseObject.createResponse(
                Constants.MESSAGE_FOUND,
                HttpStatus.OK,
                response
        );
    }

    @PostMapping("/save")
    @Operation(summary = "Save new todo", description = "create a new todo data")
    public ResponseEntity<Object> save(@RequestHeader("Authorization") String authorizationHeader,
                                       @RequestBody TodoRequest request) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        TodoResponse response = todoService.save(request, email);


        return ResponseObject.createResponse(
                Constants.MESSAGE_CREATED,
                HttpStatus.CREATED,
                response
        );
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update todo by id", description = "todo must exist")
    public ResponseEntity<Object> update(@PathVariable String id,
                                         @RequestBody TodoRequest request,
                                         @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        TodoResponse response = todoService.update(id, request, email);


        return ResponseObject.createResponse(
                Constants.MESSAGE_UPDATED,
                HttpStatus.CREATED,
                response
        );
    }

    @PutMapping("/complete/{id}")
    @Operation(summary = "Complete todo by id", description = "todo must exist")
    public ResponseEntity<Object> complete(@PathVariable String id,
                                           @RequestHeader("Authorization") String authorizationHeader) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        TodoResponse response = todoService.complete(id, email);

        return ResponseObject.createResponse(
                Constants.MESSAGE_FOUND,
                HttpStatus.OK,
                response
        );
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Remove todo by id", description = "todo must exist")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable String id) throws Exception {

        String email = jwtUtil.getEmailFromToken(authorizationHeader);

        todoService.delete(id, email);

        return ResponseObject.createResponse(
                Constants.MESSAGE_DELETED,
                HttpStatus.NO_CONTENT
        );
    }


}