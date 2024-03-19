package com.todo.api.model.response;

import com.todo.api.model.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoResponse {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime startDateTime;
    private final Duration duration;
    private final Integer priority;
    private final LocalDateTime completionTime;
    private final String createdBy;
    private final boolean isComplated;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.startDateTime = todo.getStartDateTime();
        this.duration = todo.getDuration();
        this.priority = todo.getPriority();
        this.completionTime = todo.getCompletionTime();
        this.createdBy = todo.getCreatedBy();
        this.isComplated = todo.isComplated();
    }

}
