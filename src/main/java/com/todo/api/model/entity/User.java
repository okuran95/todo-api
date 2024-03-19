package com.todo.api.model.entity;

import lombok.Getter;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Getter
public class User extends BaseEntity {

    @Version
    Long version;

    @QueryIndexed
    String email;

    String firstName;

    String lastName;

    String password;


    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }


}
