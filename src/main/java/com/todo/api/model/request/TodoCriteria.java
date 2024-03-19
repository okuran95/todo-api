package com.todo.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoCriteria {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean status;
    private String query;
    private Integer priority;

}