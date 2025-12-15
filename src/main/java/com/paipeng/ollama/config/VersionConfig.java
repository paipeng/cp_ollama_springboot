package com.paipeng.ollama.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:version.properties")
public class VersionConfig {
    @Value("${app.version}")
    private String version;
    @Value("${app.name")
    private String name;
    @Value("$app.create_date")
    private String createDate;
}
