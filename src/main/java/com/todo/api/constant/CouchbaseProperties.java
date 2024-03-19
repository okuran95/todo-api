package com.todo.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.couchbase")
@Getter
@Setter
public class CouchbaseProperties {
    private String host;
    private String username;
    private String password;
    private String bucket;
    private Boolean autoIndex;
}
