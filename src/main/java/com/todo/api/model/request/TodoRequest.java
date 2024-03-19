package com.todo.api.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoRequest {

    @NotBlank
    @Size(max = 64)
    private String title;

    @NotBlank
    @Size(max = 256)
    private String description;

    private LocalDateTime startDateTime;

    private Duration duration;

    @Min(0)
    @Max(3)
    private Integer priority;
}
