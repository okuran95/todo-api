package com.todo.api.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DateTimeUtil {

    public LocalDateTime getEndDateTimeFromDuration(LocalDateTime startDate, Duration duration) {
        return startDate.plus(duration);
    }

}
