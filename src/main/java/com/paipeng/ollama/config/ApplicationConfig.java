package com.paipeng.ollama.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Data
@Configuration
@EnableAsync
@ConfigurationProperties()
public class ApplicationConfig {

    @Bean(name="asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(300);
        threadPoolTaskExecutor.setMaxPoolSize(350);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadNamePrefix("AsyncThread-Ollama-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


    @Value("${spring.application.name")
    private String name;

    @Value("${spring.application.baseName}")
    private String baseName;

    @Value("${spring.application.defaultLocale}")
    private String defaultLocale;

    @Value("${spring.application.domain}")
    private String domain;

    @Value("${spring.application.websocketUrl}")
    private String websocketUrl;


}
