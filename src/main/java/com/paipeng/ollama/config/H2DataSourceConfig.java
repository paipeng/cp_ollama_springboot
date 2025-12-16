package com.paipeng.ollama.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class H2DataSourceConfig {
/*
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:customDb;DB_CLOSE_DELAY=-1"); // DB_CLOSE_DELAY=-1 keeps the DB alive
        dataSource.setUsername("sa");
        dataSource.setPassword("password");
        return dataSource;
    }

 */
}