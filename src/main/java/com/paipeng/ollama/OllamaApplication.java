package com.paipeng.ollama;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
public class OllamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OllamaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
		return args -> {
			System.out.println("SprintBoot started !!!!");
			String[] beanNames = applicationContext.getBeanDefinitionNames();
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
