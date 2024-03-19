package com.todo.api.repository;

import com.todo.api.model.entity.Todo;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Collection("todos")
public interface TodoRepository extends CouchbaseRepository<Todo, String>, DynamicProxyable<UserRepository> {
    Optional<Todo> findByIdAndCreatedBy(String id, String userId);

    @Query("#{#n1ql.selectEntity} " +
            "WHERE _class = 'com.todo.api.model.entity.Todo' " +
            "AND createdBy = $userId " +
            "AND ($startDate is null or dateTime >= $startDate) " +
            "AND ($endDate is null or dateTime <= $endDate) " +
            "AND ($status is null " +
            "   OR ($status = true and completionTime is not null) " +
            "   OR ($status = false and completionTime is null)) " +
            "AND ($priority is null or priority = $priority) " +
            "AND (($query is null) " +
            "   OR (LOWER(title) LIKE '%' || LOWER($query) || '%') " +
            "   OR (LOWER(description) LIKE '%' || LOWER($query) || '%'))")
    List<Todo> findByCriteria(
            String userId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean status,
            String query,
            Integer priority);
}
