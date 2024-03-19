package com.todo.api.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public BaseEntity(){
        this.setCreatedDate(LocalDateTime.now());
    }
}
