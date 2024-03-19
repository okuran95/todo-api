package com.todo.api.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Document
@Getter
public class Todo extends BaseEntity {
    @Version
    Long version;
    String title;
    String description;
    LocalDateTime startDateTime;
    Duration duration;
    Integer priority;
    LocalDateTime completionTime;
    String createdBy;

    public Todo(String title, String description, LocalDateTime startDateTime, Duration duration, Integer priority, String createdBy) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.priority = priority;
        this.createdBy = createdBy;
    }


    public void update(String title, String description, LocalDateTime startDateTime, Duration duration, Integer priority) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.priority = priority;
        setUpdatedDate(LocalDateTime.now());
    }

    public void complete() {
        this.completionTime = LocalDateTime.now();
    }

    public boolean isComplated(){
        return completionTime != null;
    }
}
