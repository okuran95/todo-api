package com.todo.api.config;

import com.couchbase.client.core.msg.kv.DurabilityLevel;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.transactions.config.TransactionsConfig;
import com.todo.api.constant.CouchbaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class Config extends AbstractCouchbaseConfiguration {

    private final CouchbaseProperties couchbaseProperties;

    public Config(CouchbaseProperties couchbaseProperties) {
        this.couchbaseProperties = couchbaseProperties;
    }


    @Override
    public String getConnectionString() {
        return this.couchbaseProperties.getHost();
    }

    @Override
    public String getUserName() {
        return this.couchbaseProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return this.couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return this.couchbaseProperties.getBucket();
    }

    @Override
    public void configureEnvironment(ClusterEnvironment.Builder builder) {
        builder.transactionsConfig(TransactionsConfig.durabilityLevel(DurabilityLevel.NONE));
    }
}
