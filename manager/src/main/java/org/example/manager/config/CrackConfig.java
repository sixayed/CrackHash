package org.example.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class CrackConfig {
    private int workerCount;
    private String alphabet;
    private int workerTimeoutSeconds;
}
