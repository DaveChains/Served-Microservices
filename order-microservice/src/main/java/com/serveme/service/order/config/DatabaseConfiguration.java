package com.serveme.service.order.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.logging.Logger;

@Configuration
@EnableMongoRepositories("com.serveme.service.order")
//@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration extends AbstractMongoConfiguration {

    Logger logger = Logger.getLogger(DatabaseConfiguration.class.getName());

    @Value("${spring.enums.mongodb.host}")
    private String host;

    @Value("${spring.enums.mongodb.port}")
    private Integer port;

    @Value("${spring.enums.mongodb.username}")
    private String username;

    @Value("${spring.enums.mongodb.database}")
    private String database;

    @Value("${spring.enums.mongodb.password}")
    private String password;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public MongoClient mongo() throws Exception {

        return new MongoClient(Collections.singletonList(new ServerAddress(host, port)),
                Collections.singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
    }

}