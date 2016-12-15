package com.epitech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

/**
 * Created by sakiir on 15/12/16.
 */

@EnableMongoHttpSession
public class                            HttpSessionConfig {
    @Bean
    public JdkMongoSessionConverter     jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter();
    }
}
