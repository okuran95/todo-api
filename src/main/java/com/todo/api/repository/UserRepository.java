package com.todo.api.repository;

import com.todo.api.model.entity.User;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.DynamicProxyable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Collection("users")
public interface UserRepository extends CouchbaseRepository<User, String>, DynamicProxyable<UserRepository> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
