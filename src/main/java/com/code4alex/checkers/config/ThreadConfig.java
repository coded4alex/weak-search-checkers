package com.code4alex.checkers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "thread-config")
@Getter @Setter
public class ThreadConfig {
    private int threadPoolSize;
}
