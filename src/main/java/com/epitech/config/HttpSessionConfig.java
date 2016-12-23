package com.epitech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;


/**
 * This class is used to configure
 * the mongodb sessions.
 */
@EnableMongoHttpSession
public class                            HttpSessionConfig {

    /**
     * Configure sessions.
     *
     * @return a JdkMongoSessionConverter Object
     * @see JdkMongoSessionConverter
     */
    @Bean
    public JdkMongoSessionConverter     jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter();
    }
}
