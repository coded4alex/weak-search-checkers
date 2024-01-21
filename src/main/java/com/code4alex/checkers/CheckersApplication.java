package com.code4alex.checkers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.code4alex.checkers.config.ThreadConfig;


@SpringBootApplication
@EnableConfigurationProperties(ThreadConfig.class)
public class CheckersApplication {
	public static void main(String[] args) {
		SpringApplication.run(CheckersApplication.class, args);
	}
}
