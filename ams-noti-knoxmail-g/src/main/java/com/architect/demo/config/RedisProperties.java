package com.architect.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.redis")
@Component
public class RedisProperties {
	private String host;
    private int port;
}
